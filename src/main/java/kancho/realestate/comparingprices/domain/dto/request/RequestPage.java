package kancho.realestate.comparingprices.domain.dto.request;

import org.springframework.data.domain.Pageable;

public class RequestPage {

	private int limit;
	private int offset;

	private RequestPage(int limit, int offset) {
		this.limit = limit;
		this.offset = offset;
	}

	public static RequestPage from(Pageable pageable){
		int limit = pageable.getPageSize();
		int offset= pageable.getPageNumber()* pageable.getPageSize();
		return new RequestPage(limit,offset);
	}

	public static RequestPage from(int limit, int offset){
		return new RequestPage(limit,offset);
	}

	public int getLimit() {
		return limit;
	}

	public int getOffset() {
		return offset;
	}
}
