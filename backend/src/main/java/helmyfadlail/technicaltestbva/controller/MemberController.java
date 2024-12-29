package helmyfadlail.technicaltestbva.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import helmyfadlail.technicaltestbva.dto.CreateMemberRequest;
import helmyfadlail.technicaltestbva.dto.MemberResponse;
import helmyfadlail.technicaltestbva.dto.PagingResponse;
import helmyfadlail.technicaltestbva.dto.SearchMemberRequest;
import helmyfadlail.technicaltestbva.dto.UpdateMemberRequest;
import helmyfadlail.technicaltestbva.dto.WebResponse;
import helmyfadlail.technicaltestbva.entity.User;
import helmyfadlail.technicaltestbva.service.MemberService;

@RestController
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping(path = "/api/members", consumes = {
            "multipart/form-data" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<MemberResponse> create(User user, @ModelAttribute CreateMemberRequest request) {

        try {
            MemberResponse memberResponse = memberService.create(user, request, request.getPictureUrl());
            return WebResponse.<MemberResponse>builder().data(memberResponse).build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(path = "/api/members/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<MemberResponse> get(User user, @PathVariable("memberId") String memberId) {
        MemberResponse memberResponse = memberService.get(user, memberId);
        return WebResponse.<MemberResponse>builder().data(memberResponse).build();
    }

    @PatchMapping(path = "/api/members/{memberId}", consumes = {
            "multipart/form-data" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<MemberResponse> update(User user, @ModelAttribute UpdateMemberRequest request) {

        try {
            MemberResponse memberResponse = memberService.update(user, request, request.getPictureUrl());
            return WebResponse.<MemberResponse>builder().data(memberResponse).build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping(path = "/api/members/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(User user, @PathVariable("memberId") String memberId) {
        memberService.delete(user, memberId);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = "/api/members", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<MemberResponse>> search(User user,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "superior", required = false) String superior,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        SearchMemberRequest request = SearchMemberRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .position(position)
                .superior(superior)
                .build();

        Page<MemberResponse> memberResponses = memberService.search(user, request);
        return WebResponse.<List<MemberResponse>>builder()
                .data(memberResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(memberResponses.getNumber())
                        .totalPage(memberResponses.getTotalPages())
                        .size(memberResponses.getSize())
                        .build())
                .build();
    }

}
