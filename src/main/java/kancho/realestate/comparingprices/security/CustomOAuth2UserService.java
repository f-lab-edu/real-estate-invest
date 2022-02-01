package kancho.realestate.comparingprices.security;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kancho.realestate.comparingprices.domain.dto.SessionUserVO;
import kancho.realestate.comparingprices.domain.model.User;
import kancho.realestate.comparingprices.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final UserService userService;
	private final HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

		OAuthAttributes attributes = OAuthAttributes.
			of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

		User user = loginOAuth2(attributes);
		httpSession.setAttribute("user", new SessionUserVO(user.getAccount(), "",user.getId()));

		return new DefaultOAuth2User(
			Collections.emptyList(),
			attributes.getAttributes(),
			attributes.getNameAttributeKey());
	}

	private User loginOAuth2(OAuthAttributes attributes) {
		User oAuth2User = userService.findUserByAccountOptional(attributes.getEmail()).orElse(attributes.toEntity());
		return userService.oAuth2Login(oAuth2User);
	}
}
