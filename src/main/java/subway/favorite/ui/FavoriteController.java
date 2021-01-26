package subway.favorite.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.favorite.application.FavoriteService;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;

import java.net.URI;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private FavoriteService favoriteService;
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity creatFavorites(@AuthenticationPrincipal LoginMember loginMember, @RequestBody FavoriteRequest favoriteRequest) {
        favoriteService.insertFavorite(favoriteRequest, loginMember.getId());
        return ResponseEntity.created(URI.create("/favorites/" + loginMember.getId())).build();
    }

    @GetMapping
    public ResponseEntity showFavorites(@AuthenticationPrincipal LoginMember loginMember) {
        FavoriteResponse favoriteResponse = favoriteService.showFavoriteByMemberId(loginMember.getId());
        return ResponseEntity.ok().body(favoriteResponse);
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity deleteFavorite(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long favoriteId) {
        favoriteService.deleteFavoriteById(loginMember.getId(), favoriteId);
        return ResponseEntity.noContent().build();
    }

}
