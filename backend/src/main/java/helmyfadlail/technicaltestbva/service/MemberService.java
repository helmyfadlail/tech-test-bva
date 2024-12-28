package helmyfadlail.technicaltestbva.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import helmyfadlail.technicaltestbva.dto.CreateMemberRequest;
import helmyfadlail.technicaltestbva.dto.MemberResponse;
import helmyfadlail.technicaltestbva.dto.SearchMemberRequest;
import helmyfadlail.technicaltestbva.dto.UpdateMemberRequest;
import helmyfadlail.technicaltestbva.entity.Member;
import helmyfadlail.technicaltestbva.entity.User;
import helmyfadlail.technicaltestbva.repository.MemberRepository;
import helmyfadlail.technicaltestbva.util.FileUploadUtil;
import jakarta.persistence.criteria.Predicate;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CloudinaryService cloudinaryService;

    private MemberResponse toMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .position(member.getPosition())
                .superior(member.getSuperior())
                .pictureUrl(member.getPictureUrl())
                .build();
    }

    @Transactional
    public MemberResponse create(User user, CreateMemberRequest request, MultipartFile file) throws IOException {
        validationService.validate(request);

        FileUploadUtil.assertAllowed(file);

        String pictureUrl = cloudinaryService.uploadFile(file);

        Member member = new Member();
        member.setId(UUID.randomUUID().toString());
        member.setName(request.getName());
        member.setPosition(request.getPosition());
        member.setSuperior(request.getSuperior());
        member.setPictureUrl(pictureUrl);
        member.setUser(user);

        memberRepository.save(member);

        return toMemberResponse(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse get(User user, String memberId) {
        Member member = memberRepository.findFirstByUserAndId(user, memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        return toMemberResponse(member);
    }

    @Transactional
    public MemberResponse update(User user, UpdateMemberRequest request, MultipartFile file) throws IOException {
        validationService.validate(request);

        Member member = memberRepository.findFirstByUserAndId(user, request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        if (file != null && !file.isEmpty()) {
            FileUploadUtil.assertAllowed(file);
            String pictureUrl = cloudinaryService.uploadFile(file);
            member.setPictureUrl(pictureUrl);
        }

        member.setName(request.getName());
        member.setPosition(request.getPosition());
        member.setSuperior(request.getSuperior());
        memberRepository.save(member);

        return toMemberResponse(member);
    }

    @Transactional
    public void delete(User user, String memberId) {
        Member member = memberRepository.findFirstByUserAndId(user, memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public Page<MemberResponse> search(User user, SearchMemberRequest request) {
        Specification<Member> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("user"), user));
            if (Objects.nonNull(request.getName())) {
                predicates.add(builder.like(root.get("name"), "%" + request.getName() + "%"));
            }
            if (Objects.nonNull(request.getPosition())) {
                predicates.add(builder.like(root.get("position"), "%" + request.getPosition() + "%"));
            }
            if (Objects.nonNull(request.getSuperior())) {
                predicates.add(builder.like(root.get("superior"), "%" + request.getSuperior() + "%"));
            }

            return query.where(predicates.toArray(new Predicate[] {})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Member> members = memberRepository.findAll(specification, pageable);

        List<MemberResponse> memberResponses = members.getContent().stream().map(this::toMemberResponse).toList();

        return new PageImpl<>(memberResponses, pageable, members.getTotalElements());
    }
}
