package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.Lecture;
import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.LectureDtoCreate;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author Oleksii_Ushakov
 */
public class LectureDtoConverterTest {
    private static final Long ID = 999L;
    private static final String TEST_TITLE = "Test name";
    private static final LocalDateTime DATE_OF_LECTURE = LocalDateTime.of(2000, 2, 2, 2, 2, 2, 2);
    private static final String TYPE_OF_PLACE = "Hostel";
    private static final String DEVICE = "Bed";
    private static final String LINK_TOPIC_EPAM = "https://www.epam-group.ru/";
    private static final String LINK_YOUTUBE = "https://www.youtube.com/?gl=UA";
    private static final String LINK_VIDEO_PORTAL_EPAM = "https://videoportal.epam.com/";
    private static final String AGENDA = "AGENDA";
    private static final String DESCRIPTION = "Good test";

    private LectureDtoConverter converter = new LectureDtoConverter();

    @Test
    public void should_convert_lecture_dto_to_lecture() {
        //GIVEN
        LectureDto lectureDto = new LectureDto();
        lectureDto.setTitle(TEST_TITLE);
        lectureDto.setId(ID);
        lectureDto.setDateOfLecture(DATE_OF_LECTURE);
        lectureDto.setTypeOfPlace(TYPE_OF_PLACE);
        lectureDto.setDevice(DEVICE);
        lectureDto.setLinkTopicEpam(LINK_TOPIC_EPAM);
        lectureDto.setLinkYoutube(LINK_YOUTUBE);
        lectureDto.setLinkVideoPortalEpam(LINK_VIDEO_PORTAL_EPAM);
        lectureDto.setAgenda(AGENDA);
        lectureDto.setDescription(DESCRIPTION);

        //WHEN
        Lecture lecture = converter.convert(lectureDto);

        //THEN
        assertLectureEquals(lecture, lectureDto);
    }

    @Test
    public void should_convert_lecture_to_lecture_dto() {
        //GIVEN
        Lecture lecture = new Lecture();
        lecture.setName(TEST_TITLE);
        lecture.setId(ID);
        lecture.setDateStart(DATE_OF_LECTURE);
        lecture.setTypeOfPlace(TYPE_OF_PLACE);
        lecture.setDeviceList(DEVICE);
        lecture.setLinkTopicEpam(LINK_TOPIC_EPAM);
        lecture.setLinkYoutube(LINK_YOUTUBE);
        lecture.setLinkVideoPortalEpam(LINK_VIDEO_PORTAL_EPAM);
        lecture.setAgenda(AGENDA);
        lecture.setDescription(DESCRIPTION);

        //WHEN
        LectureDto lectureDto = converter.convert(lecture);

        //THEN
        assertLectureEquals(lecture, lectureDto);
    }

    @Test
    public void should_convert_lecture_dto_create_to_lecture() {
        //GIVEN
        LectureDtoCreate lectureDtoCreate = new LectureDtoCreate();
        lectureDtoCreate.setTitle(TEST_TITLE);
        lectureDtoCreate.setDateOfLecture(DATE_OF_LECTURE);
        lectureDtoCreate.setTypeOfPlace(TYPE_OF_PLACE);
        lectureDtoCreate.setDevice(DEVICE);
        lectureDtoCreate.setLinkTopicEpam(LINK_TOPIC_EPAM);
        lectureDtoCreate.setLinkYoutube(LINK_YOUTUBE);
        lectureDtoCreate.setLinkVideoPortalEpam(LINK_VIDEO_PORTAL_EPAM);
        lectureDtoCreate.setAgenda(AGENDA);
        lectureDtoCreate.setDescription(DESCRIPTION);

        //WHEN
        Lecture lecture = converter.convert(lectureDtoCreate);

        //THEN
        assertLectureEquals(lecture, lectureDtoCreate);
    }

    @SuppressWarnings("checkstyle:methodname")
    private void assertLectureEquals(Lecture lecture, LectureDto lectureDto) {
        assertEquals(lecture.getId(), lectureDto.getId());
        assertEquals(lecture.getName(), lectureDto.getTitle());
        assertEquals(lecture.getDateStart(), lectureDto.getDateOfLecture());
        assertEquals(lecture.getTypeOfPlace(), lectureDto.getTypeOfPlace());
        assertEquals(lecture.getDevice(), lectureDto.getDevice());
        assertEquals(lecture.getLinkTopicEpam(), lectureDto.getLinkTopicEpam());
        assertEquals(lecture.getLinkYoutube(), lectureDto.getLinkYoutube());
        assertEquals(lecture.getLinkVideoPortalEpam(), lectureDto.getLinkVideoPortalEpam());
        assertEquals(lecture.getAgenda(), lectureDto.getAgenda());
        assertEquals(lecture.getDescription(), lectureDto.getDescription());
    }

    @SuppressWarnings("checkstyle:methodname")
    private void assertLectureEquals(Lecture lecture, LectureDtoCreate lectureDtoCreate) {
        assertEquals(lecture.getName(), lectureDtoCreate.getTitle());
        assertEquals(lecture.getDateStart(), lectureDtoCreate.getDateOfLecture());
        assertEquals(lecture.getTypeOfPlace(), lectureDtoCreate.getTypeOfPlace());
        assertEquals(lecture.getDevice(), lectureDtoCreate.getDevice());
        assertEquals(lecture.getLinkTopicEpam(), lectureDtoCreate.getLinkTopicEpam());
        assertEquals(lecture.getLinkYoutube(), lectureDtoCreate.getLinkYoutube());
        assertEquals(lecture.getLinkVideoPortalEpam(), lectureDtoCreate.getLinkVideoPortalEpam());
        assertEquals(lecture.getAgenda(), lectureDtoCreate.getAgenda());
        assertEquals(lecture.getDescription(), lectureDtoCreate.getDescription());
    }

}