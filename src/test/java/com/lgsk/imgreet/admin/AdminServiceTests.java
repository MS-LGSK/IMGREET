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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.lgsk.imgreet.admin.DTO.CommentReportDTO;
import com.lgsk.imgreet.admin.DTO.CommentReportResponseDTO;
import com.lgsk.imgreet.admin.DTO.GreetReportDTO;
import com.lgsk.imgreet.admin.DTO.GreetReportResponseDTO;
import com.lgsk.imgreet.admin.DTO.TemplateReportDTO;
import com.lgsk.imgreet.admin.DTO.TemplateReportResponseDTO;
import com.lgsk.imgreet.admin.repository.CommentReportRepository;
import com.lgsk.imgreet.admin.repository.GreetReportRepository;
import com.lgsk.imgreet.admin.repository.TemplateReportRepository;
import com.lgsk.imgreet.base.entity.Role;
import com.lgsk.imgreet.comment.repository.CommentRepository;
import com.lgsk.imgreet.entity.Comment;
import com.lgsk.imgreet.entity.CommentReport;
import com.lgsk.imgreet.entity.Greet;
import com.lgsk.imgreet.entity.GreetReport;
import com.lgsk.imgreet.entity.Template;
import com.lgsk.imgreet.entity.TemplateReport;
import com.lgsk.imgreet.entity.User;
import com.lgsk.imgreet.greet.repository.GreetRepository;
import com.lgsk.imgreet.login.repository.UserRepository;
import com.lgsk.imgreet.template.repository.TemplateRepository;

@SpringBootTest
public class AdminServiceTests {

	@Autowired private GreetReportRepository greetReportRepository;
	@Autowired private CommentReportRepository commentReportRepository;
	@Autowired private TemplateReportRepository templateReportRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private GreetRepository greetRepository;
	@Autowired private CommentRepository commentRepository;
	@Autowired private TemplateRepository templateRepository;

	User badUser;
	User reporter1;
	User reporter2;
	Greet greet;
	GreetReport greetReport1;
	GreetReport greetReport2;
	Comment comment;
	CommentReport commentReport1;
	CommentReport commentReport2;
	Template template;
	TemplateReport templateReport1;
	TemplateReport templateReport2;

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

		template = templateRepository.save(Template.builder()
			.title("혐오")
			.creatorId(badUser.getId())
			.imageUrl("ImgUrlAddress")
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

		Pageable pageable = PageRequest.of(0, 10);

		// when
		Page<GreetReportResponseDTO> greetReportResponseDTOPage = greetReportRepository.findDistinctByDone(pageable);
		List<GreetReportDTO> greetReportDTOList = new ArrayList<>();

		for (GreetReportResponseDTO greetReport : greetReportResponseDTOPage) {
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


	@Test
	@Transactional
	@DisplayName("템플릿 신고 목록 가져오기")
	void getTemplateReportList() {

		// given
		templateReport1 = templateReportRepository.save(TemplateReport.builder()
			.template(template)
			.reason("혐오")
			.ipaddress("ipAddress1")
			.done(false)
			.build());

		templateReport2 = templateReportRepository.save(TemplateReport.builder()
			.template(template)
			.reason("혐오")
			.ipaddress("ipAddress2")
			.done(false)
			.build());

		Pageable pageable = PageRequest.of(0, 10);

		// when
		List<TemplateReportDTO> templateReportDTOList = new ArrayList<>();

		Page<TemplateReportResponseDTO> templateReportPage = templateReportRepository.findDistinctByDone(pageable);
		for (TemplateReportResponseDTO templateReport : templateReportPage) {
			Template template = templateRepository.findById(templateReport.getTemplateId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 템플릿입니다."));

			TemplateReportDTO templateReportDTO = TemplateReportDTO.builder()
				.templateId(template.getId())
				.templateTitle(template.getTitle())
				.templateURL("templateUrl")
				.reason(templateReport.getReason())
				.creatorId(template.getCreatorId())
				.build();

			templateReportDTOList.add(templateReportDTO);
		}

		// then
		assertThat(templateReportDTOList).hasSize(1);
	}


	@Test
	@Transactional
	@DisplayName("댓글 신고 목록 가져오기")
	void getCommentReportList() {

		// given
		commentReport1 = commentReportRepository.save(CommentReport.builder()
			.comment(comment)
			.reason("홍보")
			.done(false)
			.build());

		commentReport2 = commentReportRepository.save(CommentReport.builder()
			.comment(comment)
			.reason("홍보")
			.done(false)
			.build());

		// when
		Pageable pageable = PageRequest.of(0, 10);
		List<CommentReportDTO> commentReportDTOList = new ArrayList<>();

		Page<CommentReportResponseDTO> commentReportList = commentReportRepository.findDistinctByDone(pageable);
		for (CommentReportResponseDTO commentReport : commentReportList) {
			Comment comment = commentRepository.findById(commentReport.getCommentId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
			Greet greet = comment.getGreet();

			CommentReportDTO commentReportDTO = CommentReportDTO.builder()
				.greetId(greet.getId())
				.greetTitle(greet.getTitle())
				.greetUrl(greet.getUrl())
				.commentId(comment.getId())
				.commentContent(comment.getContent())
				.reportReason(commentReport.getReason())
				.build();

			commentReportDTOList.add(commentReportDTO);
		}

		// then
		assertThat(commentReportDTOList).hasSize(1);
	}

}
