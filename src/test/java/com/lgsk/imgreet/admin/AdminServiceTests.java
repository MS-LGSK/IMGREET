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

import com.lgsk.imgreet.admin.DTO.CommentReportDTO;
import com.lgsk.imgreet.admin.DTO.GreetReportDTO;
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
	User reporter;
	GreetReport greetReport;
	Comment comment;
	CommentReport commentReport;

	@BeforeEach
	void setup() {

		reporter = userRepository.save(User.builder()
			.oauthId("kakao_001")
			.nickname("신고자")
			.role(Role.FREE_USER)
			.build());

		badUser = userRepository.save(User.builder()
			.oauthId("kakao_002")
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
	@DisplayName("초대장 신고 목록 가져오기")
	void getGreetReportList() {

		// given
		greetReport = greetReportRepository.save(GreetReport.builder()
			.greet(greet)
			.ipAddress("ipAddress")
			.reason("제목에 욕설이 있어요.")
			.done(false)
			.build());

		// when
		List<GreetReportDTO> greetReportDTOList = new ArrayList<>();

		List<GreetReport> greetReportList = greetReportRepository.findAllByDone(false);
		for (GreetReport greetReport : greetReportList) {
			Long greetId = greetReport.getGreet().getId();
			Greet greet = greetRepository.findById(greetId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 초대장입니다."));

			GreetReportDTO greetReportDTO = GreetReportDTO.builder()
				.reportId(greetReport.getId())
				.greetId(greetId)
				.greetTitle(greet.getTitle())
				.greetUrl(greet.getTitle())
				.reportReason(greetReport.getReason())
				.userId(greet.getUser().getId())
				.build();

			greetReportDTOList.add(greetReportDTO);
		}

		// then
		assertThat(greetReportDTOList).hasSize(1);

		GreetReportDTO getGreetReportDTO = greetReportDTOList.get(0);
		assertThat(getGreetReportDTO.getReportId()).isEqualTo(greetReport.getId());
	}


	@Test
	@Transactional
	@DisplayName("댓글 신고 목록 가져오기")
	void getCommentReportList() {

		// given
		commentReport = commnetReportRepository.save(CommentReport.builder()
			.comment(comment)
			.reason("홍보")
			.done(false)
			.build());

		// when
		List<CommentReportDTO> commentReportDTOList = new ArrayList<>();

		List<CommentReport> commentReportList = commnetReportRepository.findAllByDone(false);
		for (CommentReport commentReport : commentReportList) {
			Long commentId = commentReport.getComment().getId();
			Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
			Greet greet = comment.getGreet();

			CommentReportDTO commentReportDTO = CommentReportDTO.builder()
				.reportId(commentReport.getId())
				.greetId(greet.getId())
				.greetTitle(greet.getTitle())
				.greetUrl(greet.getTitle())
				.commentId(commentId)
				.commentContent(comment.getContent())
				.reportReason(commentReport.getReason())
				.build();

			commentReportDTOList.add(commentReportDTO);
		}

		// then
		assertThat(commentReportDTOList).hasSize(1);

		CommentReportDTO getCommentReportDTO = commentReportDTOList.get(0);
		assertThat(getCommentReportDTO.getReportId()).isEqualTo(commentReport.getId());
	}
}
