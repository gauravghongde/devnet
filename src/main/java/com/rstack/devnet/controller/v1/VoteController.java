package com.rstack.devnet.controller.v1;

import com.rstack.devnet.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1")
@CrossOrigin(origins = "*")
public class VoteController {

    @Autowired
    private VoteService voteService;

    /////////// VOTING SYSTEM ///////////////
    /*
    BUD
    000 -> 0 -> No BM, No UV, No DV
    001 -> 1 -> No BM, No UV, DV
    010 -> 2 -> No BM, UV, No DV

    100 -> 4 -> BM, No UV, No DV
    101 -> 5 -> BM, No UV, DV
    110 -> 6 -> BM, UV, No DV
    */

    @GetMapping(value = "/posts/{postId}/vote")
    public ResponseEntity<?> addVote(@PathVariable String postId,
                                  @RequestParam(value = "commentId", defaultValue = "") String commentId,
                                  @RequestParam("voteId") int voteId,
                                  Authentication authentication
    ) {
        String username = authentication.getName();
        String message = voteService.addVote(username, voteId, postId, commentId);
        return ResponseEntity.ok(message);
    }
}
