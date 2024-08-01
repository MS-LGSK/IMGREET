package com.lgsk.imgreet.admin;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.lgsk.imgreet.admin.DTO.GreetReportDTO;
import com.lgsk.imgreet.admin.DTO.GreetReportResponseDTO;
import com.lgsk.imgreet.admin.repository.CommnetReportRepository;
import com.lgsk.imgreet.admin.repository.GreetReportRepository;
import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.comment.repository.CommentRepository;
import com.lgsk.imgreet.entity.Comment;
import com.lgsk.imgreet.entity.CommentReport;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.entity.GreetReport;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.greet.repository.GreetRepository;
import com.lgsk.imgreet.login.repository.UserRepository;

@SpringBootTest
public class AdminServiceTests {

	@Autowired
	private GreetReportRepository greetReportRepository;

	@Autowired
	private CommnetReportRepository commnetReportRepository;

	@Autowired
	private GreetRepository greetRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentRepository commentRepository;

	Greet greet;
	User badUser;
	User reporter1;
	User reporter2;
	GreetReport greetReport1;
	GreetReport greetReport2;
	Comment comment;
	CommentReport commentReport;

	@BeforeEach
	void setup() {

		reporter1 = userRepository.save(User.builder()
			.oauthId("kakao_001")
			.nickname("신고자1")
			.role(Role.FREE_USER)
			.build());

		reporter2 = userRepository.save(User.builder()
			.oauthId("kakao_002")
			.nickname("신고자2")
			.role(Role.FREE_USER)
			.build());

		badUser = userRepository.save(User.builder()
			.oauthId("kakao_003")
			.nickname("Bad Creator")
			.role(Role.FREE_USER)
			.build());

		greet = greetRepository.save(Greet.builder()
			.title("욕설")
			.user(badUser)
			.url("URLAddress")
			.imageUrl("ImageURLAddress")
			.allowComments(true)
			.expireDate(LocalDateTime.now().plusDays(5))
			.build());

		comment = commentRepository.save(Comment.builder()
			.greet(greet)
			.ipAddress("IpAddress")
			.nickname("홍보")
			.password("1234")
			.content("굉장히 좋고 저렴해요")
			.build());

	}


	@Test
	@Transactional
	@DisplayName("초대장 신고 저장하기")
	void getReportGreet() {
		greetReport1 = greetReportRepository.save(GreetReport.builder()
			.greet(greet)
			.ipAddress("ipAddress1")
			.reason("욕설")
			.done(false)
			.build());

		greetReport2 = greetReportRepository.save(GreetReport.builder()
			.greet(greet)
			.ipAddress("ipAddress2")
			.reason("욕설")
			.done(false)
			.build());
	}

	@Test
	@Transactional
	@DisplayName("초대장 신고 목록 가져오기")
	void getGreetReportList() {

		// given
		greetReport1 = greetReportRepository.save(GreetReport.builder()
			.greet(greet)
			.ipAddress("ipAddress3")
			.reason("욕설")
			.done(false)
			.build());

		greetReport2 = greetReportRepository.save(GreetReport.builder()
			.greet(greet)
			.ipAddress("ipAddress4")
			.reason("욕설")
			.done(false)
			.build());

		// when
		List<GreetReportDTO> greetReportDTOList = new ArrayList<>();

		List<GreetReportResponseDTO> greetReportResponseDTOList = greetReportRepository.findDistinctByDone();
		for (GreetReportResponseDTO greetReport : greetReportResponseDTOList) {
			Greet greet = greetRepository.findById(greetReport.getGreetId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 초대장입니다."));

			GreetReportDTO greetReportDTO = GreetReportDTO.builder()
				.greetId(greetReport.getGreetId())
				.greetTitle(greet.getTitle())
				.greetUrl(greet.getTitle())
				.reason(greetReport.getReason())
				.userId(greet.getUser().getId())
				.build();

			greetReportDTOList.add(greetReportDTO);
		}

		// then
		assertThat(greetReportDTOList).hasSize(1);

		GreetReportDTO greetReportList = greetReportDTOList.get(0);
		assertThat(greetReportList.getGreetId()).isEqualTo(greet.getId());
	}



}
