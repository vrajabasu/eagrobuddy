package com.eagro.service.component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.eagro.entities.KPI;
import com.eagro.entities.Layout;
import com.eagro.entities.Section;
import com.eagro.entities.SectionSensorMapping;
import com.eagro.entities.Segment;
import com.eagro.entities.Sensor;
import com.eagro.entities.SensorCoverageRange;
import com.eagro.entities.enumeration.ZoneType;

@Component
public class MockDataService {

	public Layout mockLayoutData() {
		Layout layout = new Layout();
		layout.setLayoutId(1L);
		layout.setActiveFlag(true);
		layout.setCreatedBy("Rajee");
		//layout.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		layout.setWidthX(58.0);
		layout.setHeightY(30.0);
		layout.setLayoutDesc("Overall Layout visualization showing sections and segments within the sections");
		layout.setLayoutName("Overall Layout");
		//layout.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		layout.setUpdatedBy("V");
		return layout;
	}
	
	public List<Section> mockSectionListByLayoutId() {
		List<Section> sectionList = new ArrayList<Section>();
		
		Layout layout = new Layout();
		layout.setLayoutId(1L);
		layout.setActiveFlag(true);
		layout.setCreatedBy("Rajee");
		//layout.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		layout.setWidthX(58.0);
		layout.setHeightY(30.0);
		layout.setLayoutDesc("Overall Layout visualization showing sections and segments within the sections");
		layout.setLayoutName("Overall Layout");
		//layout.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		layout.setUpdatedBy("V"); 
		
		Section section1 = new Section();
		section1.setSectionId(1L);
		section1.setActiveFlag(true);
		section1.setCreatedBy("Rajee");
		//section1.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		section1.setEndX(22.5);
		section1.setEndY(11.5);
		section1.setLayout(layout);
		section1.setSectionDesc("Greens Desc");
		section1.setSectionName("Greens");
		section1.setStartX(12.5);
		section1.setStartY(2.5);
		section1.setUpdatedBy("Veera");
		//section1.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		
		Section section2 = new Section();
		section2.setSectionId(2L);
		section2.setActiveFlag(true);
		section2.setCreatedBy("R");
		//section2.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		section2.setEndX(35.0);
		section2.setEndY(11.5);
		section2.setLayout(layout);
		section2.setSectionDesc("Lettuce Desc");
		section2.setSectionName("Lettuce");
		section2.setStartX(2.05);
		section2.setStartY(2.5);
		section2.setUpdatedBy("V");
		//section2.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		Section section3 = new Section();
		section3.setSectionId(3L);
		section3.setActiveFlag(true);
		section3.setCreatedBy("R");
		//section3.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		section3.setEndX(55.5);
		section3.setEndY(11.5);
		section3.setLayout(layout);
		section3.setSectionDesc("Cherry Tomato Desc");
		section3.setSectionName("Cherry Tomato");
		section3.setStartX(37.5);
		section3.setStartY(2.5);
		section3.setUpdatedBy("V");
		//section3.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		Section section4 = new Section();
		section4.setSectionId(4L);
		section4.setActiveFlag(true);
		section4.setCreatedBy("R");
		//section4.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		section4.setEndX(55.5);
		section4.setEndY(20.0);
		section4.setLayout(layout);
		section4.setSectionDesc("Bean Desc");
		section4.setSectionName("Bean");
		section4.setStartX(12.5);
		section4.setStartY(15.0);
		section4.setUpdatedBy("V");
		//section4.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		sectionList.add(section1);
		sectionList.add(section2);
		sectionList.add(section3);
		sectionList.add(section4);
		
		
		return sectionList;
	}
	
	public List<Segment> getSegmentbyLayoutAndSectionId() {
		List<Segment> segmentList = new ArrayList<>();
		Layout layout =  mockLayoutData();
		Section section1 = new Section();
		section1.setSectionId(1L);
		section1.setActiveFlag(true);
		section1.setCreatedBy("Rajee");
		//section1.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		section1.setEndX(22.5);
		section1.setEndY(11.5);
		section1.setLayout(layout);
		section1.setSectionDesc("Greens Desc");
		section1.setSectionName("Greens");
		section1.setStartX(12.5);
		section1.setStartY(2.5);
		section1.setUpdatedBy("Veera");
		//section1.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		Segment segment1 = new Segment();
		segment1.setSegmentId(1l);
		segment1.setSegmentDesc("A segmentDesc");
		segment1.setSegmentName("A");
		segment1.setSection(section1);
		segment1.setActiveFlag(true);
		segment1.setCreatedBy("R");
		//segment1.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		segment1.setEndX(3.75);
		segment1.setEndY(3.5);
		segment1.setLayout(layout);
		segment1.setSection(section1);
		segment1.setStartX(0.0);
		segment1.setStartY(0.0);
		
		
		Segment segment2 = new Segment();
		segment2.setSegmentId(1l);
		segment2.setSegmentDesc("B segmentDesc");
		segment2.setSegmentName("B");
		segment2.setSection(section1);
		segment2.setActiveFlag(true);
		segment2.setCreatedBy("R");
		//segment2.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		segment2.setEndX(6.25);
		segment2.setEndY(3.5);
		segment2.setLayout(layout);
		segment2.setSection(section1);
		segment2.setStartX(3.75);
		segment2.setStartY(0.0);
		
		
		Segment segment3 = new Segment();
		segment3.setSegmentId(1l);
		segment3.setSegmentDesc("C segmentDesc");
		segment3.setSegmentName("C");
		segment3.setSection(section1);
		segment3.setActiveFlag(true);
		segment3.setCreatedBy("R");
		//segment3.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		segment3.setEndX(10.0);
		segment3.setEndY(3.5);
		segment3.setLayout(layout);
		segment3.setSection(section1);
		segment3.setStartX(6.25);
		segment3.setStartY(0.0);
		
		Segment segment4 = new Segment();
		segment4.setSegmentId(1l);
		segment4.setSegmentDesc(" D segmentDesc");
		segment4.setSegmentName("D");
		segment4.setSection(section1);
		segment4.setActiveFlag(true);
		segment4.setCreatedBy("R");
		//segment4.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		segment4.setEndX(3.75);
		segment4.setEndY(5.5);
		segment4.setLayout(layout);
		segment4.setSection(section1);
		segment4.setStartX(0.0);
		segment4.setStartY(3.5);
		
		Segment segment5 = new Segment();
		segment5.setSegmentId(1l);
		segment5.setSegmentDesc("E segmentDesc");
		segment5.setSegmentName("E");
		segment5.setSection(section1);
		segment5.setActiveFlag(true);
		segment5.setCreatedBy("R");
		//segment5.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		segment5.setEndX(6.25);
		segment5.setEndY(5.5);
		segment5.setLayout(layout);
		segment5.setSection(section1);
		segment5.setStartX(3.75);
		segment5.setStartY(3.5);
		
		Segment segment6 = new Segment();
		segment6.setSegmentId(1l);
		segment6.setSegmentDesc("F segmentDesc");
		segment6.setSegmentName("F");
		segment6.setSection(section1);
		segment6.setActiveFlag(true);
		segment6.setCreatedBy("R");
		//segment6.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		segment6.setEndX(10.0);
		segment6.setEndY(5.5);
		segment6.setLayout(layout);
		segment6.setSection(section1);
		segment6.setStartX(6.25);
		segment6.setStartY(3.5);
		
		segmentList.add(segment1);
		segmentList.add(segment2);
		segmentList.add(segment3);
		segmentList.add(segment4);
		segmentList.add(segment5);
		segmentList.add(segment6);
				
		return segmentList;
		
	}
	
	public List<SectionSensorMapping> sectionSensorMappingList(){
		List<SectionSensorMapping> sensorList = new ArrayList<>();
		Layout layout =  mockLayoutData();
		Sensor sensor1 = new Sensor();
		sensor1.setId(1L);
		sensor1.setLayout(layout);
		sensor1.setCreatedBy("Rajee");
		//sensor1.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		sensor1.setActiveFlag(true);
		sensor1.setSensorId(1L);
		sensor1.setSensorName("Sensor1");
		sensor1.setSensorDesc("Sensor1");
		sensor1.setUpdatedBy("Veera");
		//sensor1.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		Section section1 = new Section();
		section1.setSectionId(1L);
		section1.setActiveFlag(true);
		section1.setCreatedBy("Rajee");
		//section1.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		section1.setEndX(22.5);
		section1.setEndY(11.5);
		section1.setLayout(layout);
		section1.setSectionDesc("Greens Desc");
		section1.setSectionName("Greens");
		section1.setStartX(12.5);
		section1.setStartY(2.5);
		section1.setUpdatedBy("Veera");
		//section1.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		SectionSensorMapping sensorMapping = new SectionSensorMapping();
		sensorMapping.setId(1L);
		sensorMapping.setLayout(layout);
		sensorMapping.setPosX(0.0);
		sensorMapping.setPosY(4.5);
		sensorMapping.setCreatedBy("R");
		//sensorMapping.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		sensorMapping.setUpdatedBy("V");
		//sensorMapping.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		sensorMapping.setSection(section1);
		sensorMapping.setSensor(sensor1);
		sensorMapping.setZoneType(ZoneType.WATER);
		
		SectionSensorMapping sensorMapping1 = new SectionSensorMapping();
		sensorMapping1.setId(2L);
		sensorMapping1.setLayout(layout);
		sensorMapping1.setPosX(2.5);
		sensorMapping1.setPosY(2.5);
		sensorMapping1.setCreatedBy("R");
		//sensorMapping1.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		sensorMapping1.setUpdatedBy("V");
		//sensorMapping1.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		sensorMapping1.setSection(section1);
		sensorMapping1.setSensor(sensor1);
		sensorMapping1.setZoneType(ZoneType.CANOPY);
		
		Sensor sensor2 = new Sensor();
		sensor2.setId(2L);
		sensor2.setLayout(layout);
		sensor2.setCreatedBy("Rajee");
		//sensor2.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		sensor2.setActiveFlag(true);
		sensor2.setSensorId(2L);
		sensor2.setSensorName("Sensor2");
		sensor2.setSensorDesc("Sensor2");
		sensor2.setUpdatedBy("Veera");
		//sensor2.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		SectionSensorMapping sensorMapping2 = new SectionSensorMapping();
		sensorMapping2.setId(2L);
		sensorMapping2.setLayout(layout);
		sensorMapping2.setPosX(5.0);
		sensorMapping2.setPosY(2.5);
		sensorMapping2.setCreatedBy("R");
		//sensorMapping2.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		sensorMapping2.setUpdatedBy("V");
		//sensorMapping2.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		sensorMapping2.setSection(section1);
		sensorMapping2.setSensor(sensor2);
		sensorMapping2.setZoneType(ZoneType.CANOPY);
		
		Sensor sensor3 = new Sensor();
		sensor3.setId(3L);
		sensor3.setLayout(layout);
		sensor3.setCreatedBy("Rajee");
		//sensor3.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		sensor3.setActiveFlag(true);
		sensor3.setSensorId(3L);
		sensor3.setSensorName("sensor3");
		sensor3.setSensorDesc("sensor3");
		sensor3.setUpdatedBy("Veera");
		//sensor3.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		SectionSensorMapping sensorMapping3 = new SectionSensorMapping();
		sensorMapping3.setId(2L);
		sensorMapping3.setLayout(layout);
		sensorMapping3.setPosX(7.5);
		sensorMapping3.setPosY(2.5);
		sensorMapping3.setCreatedBy("R");
		//sensorMapping3.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		sensorMapping3.setUpdatedBy("V");
		//sensorMapping3.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		sensorMapping3.setSection(section1);
		sensorMapping3.setSensor(sensor2);
		sensorMapping3.setZoneType(ZoneType.CANOPY);
		
		sensorList.add(sensorMapping);
		sensorList.add(sensorMapping1);
		sensorList.add(sensorMapping2);
		sensorList.add(sensorMapping3);
		
		return sensorList;
	}
	
	
	
	public List<SectionSensorMapping> waterSensorMappingList(){
		List<SectionSensorMapping> sensorList = new ArrayList<>();
		Layout layout =  mockLayoutData();
		Sensor sensor1 = new Sensor();
		sensor1.setId(1L);
		sensor1.setLayout(layout);
		sensor1.setCreatedBy("Rajee");
		//sensor1.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		sensor1.setActiveFlag(true);
		sensor1.setSensorId(1L);
		sensor1.setSensorName("Sensor1");
		sensor1.setSensorDesc("Sensor1");
		sensor1.setUpdatedBy("Veera");
		//sensor1.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		Section section1 = new Section();
		section1.setSectionId(1L);
		section1.setActiveFlag(true);
		section1.setCreatedBy("Rajee");
		//section1.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		section1.setEndX(22.5);
		section1.setEndY(11.5);
		section1.setLayout(layout);
		section1.setSectionDesc("Greens Desc");
		section1.setSectionName("Greens");
		section1.setStartX(12.5);
		section1.setStartY(2.5);
		section1.setUpdatedBy("Veera");
		//section1.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		SectionSensorMapping sensorMapping = new SectionSensorMapping();
		sensorMapping.setId(1L);
		sensorMapping.setLayout(layout);
		sensorMapping.setPosX(0.0);
		sensorMapping.setPosY(4.5);
		sensorMapping.setCreatedBy("R");
		//sensorMapping.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		sensorMapping.setUpdatedBy("V");
		//sensorMapping.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		sensorMapping.setSection(section1);
		sensorMapping.setSensor(sensor1);
		sensorMapping.setZoneType(ZoneType.WATER);
		
	
		
		sensorList.add(sensorMapping);
		
		return sensorList;
	}
	
	public SensorCoverageRange currentSensorCoverageRange() {
		SensorCoverageRange sensorCoverageRange = new SensorCoverageRange();
		
		Layout layout = new Layout();
		layout.setLayoutId(1L);
		layout.setActiveFlag(true);
		layout.setCreatedBy("Rajee");
		//layout.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		layout.setWidthX(58.0);
		layout.setHeightY(30.0);
		layout.setLayoutDesc("Overall Layout visualization showing sections and segments within the sections");
		layout.setLayoutName("Overall Layout");
		//layout.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		layout.setUpdatedBy("V");
		
		Section section1 = new Section();
		section1.setSectionId(1L);
		section1.setActiveFlag(true);
		section1.setCreatedBy("Rajee");
		//section1.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		section1.setEndX(22.5);
		section1.setEndY(11.5);
		section1.setLayout(layout);
		section1.setSectionDesc("Greens Desc");
		section1.setSectionName("Greens");
		section1.setStartX(12.5);
		section1.setStartY(2.5);
		section1.setUpdatedBy("Veera");
		//section1.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		Sensor sensor1 = new Sensor();
		sensor1.setId(1L);
		sensor1.setLayout(layout);
		sensor1.setCreatedBy("Rajee");
		//sensor1.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		sensor1.setActiveFlag(true);
		sensor1.setSensorId(1L);
		sensor1.setSensorName("Sensor1");
		sensor1.setSensorDesc("Sensor1");
		sensor1.setUpdatedBy("Veera");
		//sensor1.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		
		sensorCoverageRange.setId(1L);
		sensorCoverageRange.setLayout(layout);
		sensorCoverageRange.setSection(section1);
		sensorCoverageRange.setSensor(sensor1);
		sensorCoverageRange.setActiveFlag(true);
		sensorCoverageRange.setCreatedBy("Rajee");
		//sensorCoverageRange.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		sensorCoverageRange.setEndX(90.0);
		sensorCoverageRange.setEndY(44.0);
		sensorCoverageRange.setStartX(0.0);
		sensorCoverageRange.setStartY(0.0);
		sensorCoverageRange.setUpdatedBy("V");
		//sensorCoverageRange.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		return sensorCoverageRange;
	}
	
	public List<KPI> getKpiList() {
		List<KPI> kpiList = new ArrayList<>();
		Layout layout = new Layout();
		layout.setLayoutId(1L);
		layout.setActiveFlag(true);
		layout.setCreatedBy("Rajee");
		//layout.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		layout.setWidthX(58.0);
		layout.setHeightY(30.0);
		layout.setLayoutDesc("Overall Layout visualization showing sections and segments within the sections");
		layout.setLayoutName("Overall Layout");
		//layout.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02));
		layout.setUpdatedBy("V");
		
		Section section1 = new Section();
		section1.setSectionId(1L);
		section1.setActiveFlag(true);
		section1.setCreatedBy("Rajee");
		//section1.setCreatedDate(LocalDate.of(2017, Month.AUGUST, 02));
		section1.setEndX(22.5);
		section1.setEndY(11.5);
		section1.setLayout(layout);
		section1.setSectionDesc("Greens Desc");
		section1.setSectionName("Greens");
		section1.setStartX(12.5);
		section1.setStartY(2.5);
		section1.setUpdatedBy("Veera");
		//section1.setUpdatedDate(LocalDate.of(2018, Month.FEBRUARY, 02)); 
		
		KPI phKpi = new KPI();
		phKpi.setKpiId(1L);
		phKpi.setKpiName("PH");
		phKpi.setLayout(layout);
		phKpi.setSection(section1);
		phKpi.setZoneType(ZoneType.CANOPY);
		phKpi.setLowerRefLimit(5.5);
		phKpi.setUpperRefLimit(6.7);
		phKpi.setDeviationRange(0.2);
		phKpi.setOptimalValue(5.6);
		
		KPI ecKpi = new KPI();
		ecKpi.setKpiId(1L);
		ecKpi.setKpiName("EC");
		ecKpi.setLayout(layout);
		ecKpi.setSection(section1);
		ecKpi.setZoneType(ZoneType.CANOPY);
		ecKpi.setLowerRefLimit(1.5);
		ecKpi.setUpperRefLimit(2.0);
		ecKpi.setDeviationRange(0.25);
		ecKpi.setOptimalValue(1.8);
		
		kpiList.add(ecKpi);
		kpiList.add(phKpi);
		
		return kpiList;
	}
}
