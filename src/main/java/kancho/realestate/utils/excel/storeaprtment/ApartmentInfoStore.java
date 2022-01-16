package kancho.realestate.utils.excel.storeaprtment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.repository.ApartmentRepository;
import kancho.realestate.utils.excel.storeaprtment.domain.ApartmentlField;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ApartmentInfoStore implements ApplicationRunner {

	private static final String DATA_PATH = "realestate-prices";
	private static final Logger logger = LoggerFactory.getLogger(ApartmentInfoStore.class);
	private static final int APARTMENT_SHEET_INDEX = 1;
	private static final int DATA_ROW_START_INDEX = 1;

	private final ApartmentRepository apartmentRepository;

	@Override
	public void run(ApplicationArguments args) throws IOException {
		logger.info("start application");
		List<String> fileNames = searchFileNames(DATA_PATH);
		saveData(fileNames);
		logger.info("end application");
	}

	private List<String> searchFileNames(String dirPath) throws IOException {
		return Files.list(Paths.get(dirPath))
			.map(path -> path.getFileName().toString())
			.filter(fileName -> fileName.endsWith("xlsx"))
			.collect(Collectors.toList());
	}

	private void saveData(List<String> fileNames) {
		fileNames.stream()
			.forEach(fileName -> saveData(fileName));
	}

	private void saveData(String fileName) {
		logger.info("filename : {}", fileName);
		List<Apartment> apartments = new ArrayList<>();
		try (XSSFWorkbook workbook = new XSSFWorkbook(new File(DATA_PATH + "/" + fileName))) {
			XSSFSheet apartmentDataSheet = workbook.getSheetAt(APARTMENT_SHEET_INDEX); // 아파트 정보
			apartments = getApartments(apartmentDataSheet);
		} catch (IOException | InvalidFormatException ex) {
			logger.info("{} 파일 참조에 오류가 발생하였습니다.", fileName);
		}
		saveApartmentAll(apartments);
	}

	private List<Apartment> getApartments(XSSFSheet sheet) {
		List<Apartment> apartments = new ArrayList<>();
		for (int rowIdx = DATA_ROW_START_INDEX; rowIdx < sheet.getPhysicalNumberOfRows(); rowIdx++) {
			XSSFRow row = sheet.getRow(rowIdx);
			if (row == null) {
				continue;
			}
			apartments.add(makeApartmentInfo(row));
		}
		return apartments;
	}

	private Apartment makeApartmentInfo(XSSFRow row) {
		return new Apartment(getField(row, ApartmentlField.REGIONAL_CODE.getIdx()),
			getField(row, ApartmentlField.CITY.getIdx()),
			getField(row, ApartmentlField.GU.getIdx()), getField(row, ApartmentlField.DONG.getIdx()),
			getField(row, ApartmentlField.JIBUN.getIdx()), getField(row, ApartmentlField.BUBUN.getIdx()),
			getField(row, ApartmentlField.BONBUN.getIdx()), getField(row, ApartmentlField.APARTMENT_NAME.getIdx()),
			Integer.parseInt(getField(row, ApartmentlField.BUILD_YEAR.getIdx())),
			getField(row, ApartmentlField.ROAD_ADDRESS.getIdx()));
	}

	private String getField(XSSFRow row, int fieldIdx) {
		XSSFCell cell = row.getCell(fieldIdx);
		return cell.getStringCellValue();
	}

	private void saveApartmentAll(List<Apartment> apartments) {
		apartments.stream()
			.forEach(apartment -> saveApartment(apartment));
	}

	private void saveApartment(Apartment apartment) {
		if (isNew(apartment)) {
			apartmentRepository.save(apartment);
		}
	}

	private boolean isNew(Apartment apartment) {
		Optional<Apartment> findApartment = apartmentRepository.findApartByDistinctFields(
			apartment.getRegionalCode(), apartment.getDong(), apartment.getJibun(), apartment.getApartmentName());
		return findApartment.isEmpty();
		// return true;
	}

}
