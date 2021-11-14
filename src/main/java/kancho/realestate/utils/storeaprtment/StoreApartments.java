package kancho.realestate.utils.storeaprtment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Component;

import kancho.realestate.comparingprices.domain.model.Apartment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StoreApartments {

	private final ApartmentMapper apartmentMapper;

	@PostConstruct
	public void init() {
		System.out.println("start read data");
		readDatas();
		System.out.println("end read data");
	}

	public void readDatas() {
		String filePath = "src/main/resources/realestate-prices/2020_11_10-2021_11_09.xlsx";
		List<Apartment> apartments = new ArrayList<>();
		try (FileInputStream file = new FileInputStream(filePath)) {
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(1); // 아파트 정보

			Map<Integer, String> fieldInfo = setFieldInfo(sheet.getRow(0)); // 필드명
			apartments = getApartments(fieldInfo,sheet);
		} catch (IOException exception) {
			System.out.println("file io exception");
			exception.printStackTrace();
		} catch (Exception e) {
			System.out.println("unexpected exception");
		}
		saveApartments(apartments);
	}

	private Map<Integer, String> setFieldInfo(XSSFRow row) {
		Map<Integer, String> map = new HashMap<>();
		for (int colIdx = 0; colIdx < row.getPhysicalNumberOfCells(); colIdx++) {
			XSSFCell cell = row.getCell(colIdx);
			if (cell == null) {
				continue;
			}
			map.put(colIdx, cell.getStringCellValue());
		}
		return map;
	}

	private void saveApartments(List<Apartment> apartments) {
		for (Apartment apartment : apartments) {
			apartmentMapper.save(apartment);
		}
	}

	private List<Apartment> getApartments(Map<Integer, String> fieldInfo, XSSFSheet sheet) {
		List<Apartment> apartments = new ArrayList<>();
		for (int rowIdx = 1; rowIdx < sheet.getPhysicalNumberOfRows(); rowIdx++) {
			XSSFRow row = sheet.getRow(rowIdx);
			if (row == null) {
				continue;
			}
			apartments.add(makeApartmentInfo(fieldInfo, row));
		}
		return apartments;
	}

	private Apartment makeApartmentInfo(Map<Integer, String> fieldInfo, XSSFRow row) {
		String regionalCode = "";
		String city = "";
		String gu = "";
		String dong = "";
		String jibun = "";
		String bonbun = "";
		String bubun = "";
		String apartmentName = "";
		int buildYear = 0;
		String roadAddress = "";

		for (int colIdx = 0; colIdx <= row.getPhysicalNumberOfCells(); colIdx++) {

			XSSFCell cell = row.getCell(colIdx);

			if (cell == null) {
				continue;
			}

			switch (fieldInfo.get(colIdx)) {
				case "regional_code":
					regionalCode = cell.getStringCellValue();
					break;
				case "city":
					city = cell.getStringCellValue();
					break;
				case "gu":
					gu = cell.getStringCellValue();
					break;
				case "dong":
					dong = cell.getStringCellValue();
					break;
				case "jibun":
					jibun = cell.getStringCellValue();
					break;
				case "bonbun":
					bonbun = cell.getStringCellValue();
					break;
				case "bubun":
					bubun = cell.getStringCellValue();
					break;
				case "apartment_name":
					apartmentName = cell.getStringCellValue();
					break;
				case "build_year":
					buildYear = Integer.parseInt(cell.getStringCellValue());
					break;
				case "road_address":
					roadAddress = cell.getStringCellValue();
					break;
			}
		}
		return new Apartment(regionalCode, city, gu, dong, jibun, bonbun, bubun, apartmentName, buildYear, roadAddress);
	}
}
