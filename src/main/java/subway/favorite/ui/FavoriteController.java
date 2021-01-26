package subway.favorite.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.exceptions.AuthorizationException;
import subway.favorite.application.FavoriteService;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<Void> createFavorite(@AuthenticationPrincipal LoginMember loginMember,
                                               @RequestBody FavoriteRequest favoriteRequest) {
        if (loginMember == null) {
            throw new AuthorizationException("Only members can use this feature");
        }
        long favoriteId = favoriteService.insert(loginMember.getId(), favoriteRequest);
        return ResponseEntity.created(URI.create("/favorites/" + favoriteId)).build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getFavorites(@AuthenticationPrincipal LoginMember loginMember) {
        if (loginMember == null) {
            throw new AuthorizationException("Only members can use this feature");
        }
        return ResponseEntity.ok().body(favoriteService.find(loginMember.getId()));
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(@AuthenticationPrincipal LoginMember loginMember,
                                               @PathVariable long favoriteId) {
        if (loginMember == null) {
            throw new AuthorizationException("Only members can use this feature");
        }
        favoriteService.delete(favoriteId);
        return ResponseEntity.noContent().build();
    }
}
