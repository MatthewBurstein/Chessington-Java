package training.chessington.model;

import org.junit.Before;
import org.junit.Test;
import training.chessington.model.pieces.King;
import training.chessington.model.pieces.Pawn;
import training.chessington.model.pieces.Piece;
import training.chessington.model.pieces.Rook;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckTests {

    private Board board;
    private King king = new King(PlayerColour.WHITE);

    @Before
    public void setup() {
        board = Board.empty();
    }

    @Test
    public void kingCannotMoveIntoCheck() {
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, king);

        Piece opponent = new Rook(PlayerColour.BLACK);
        board.placePiece(new Coordinates(0, 5), opponent);

        Game game = new Game(board);
        List<Move> allowedMoves = game.getAllowedMoves(coords);

        assertThat(allowedMoves).doesNotContain(
                new Move(coords, new Coordinates(3, 5)),
                new Move(coords, new Coordinates(4, 5)),
                new Move(coords, new Coordinates(5, 5))
        );
    }

    @Test
    public void friendlyPieceCannotExposeKingToCheck() {
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, king);

        Piece friendlyPiece = new Pawn(PlayerColour.WHITE);
        Coordinates friendlyCoords = new Coordinates(4, 5);
        board.placePiece(friendlyCoords, friendlyPiece);

        Piece opponent = new Rook(PlayerColour.BLACK);
        board.placePiece(new Coordinates(4, 7), opponent);

        Game game = new Game(board);
        List<Move> allowedMoves = game.getAllowedMoves(friendlyCoords);

        assertThat(allowedMoves).isEmpty();
    }

    @Test
    public void kingCanOnlyMoveOutOfCheck() {
        Coordinates coords = new Coordinates(0, 0);
        board.placePiece(coords, king);

        Piece opponent = new Rook(PlayerColour.BLACK);
        board.placePiece(new Coordinates(0, 7), opponent);

        Game game = new Game(board);
        List<Move> allowedMoves = game.getAllowedMoves(coords);

        assertThat(allowedMoves).containsExactlyInAnyOrder(
                new Move(coords, new Coordinates(1, 0)),
                new Move(coords, new Coordinates(1, 1))
        );
    }

    @Test
    public void friendlyPieceCanOnlyBlockCheck() {
        Coordinates coords = new Coordinates(0, 0);
        board.placePiece(coords, king);

        Piece friendlyPiece = new Rook(PlayerColour.WHITE);
        Coordinates friendlyCoords = new Coordinates(7, 1);
        board.placePiece(friendlyCoords, friendlyPiece);

        Piece opponent = new Rook(PlayerColour.BLACK);
        board.placePiece(new Coordinates(0, 7), opponent);

        Game game = new Game(board);
        List<Move> allowedMoves = game.getAllowedMoves(friendlyCoords);

        assertThat(allowedMoves).containsExactlyInAnyOrder(new Move(friendlyCoords, new Coordinates(0, 1)));
    }

    @Test
    public void kingCanCaptureCheckingPiece() {
        Coordinates coords = new Coordinates(0, 0);
        board.placePiece(coords, king);

        Piece opponent = new Rook(PlayerColour.BLACK);
        board.placePiece(new Coordinates(0, 1), opponent);

        Game game = new Game(board);
        List<Move> allowedMoves = game.getAllowedMoves(coords);

        assertThat(allowedMoves).contains(
                new Move(coords, new Coordinates(0, 1))
        );
    }

    @Test
    public void friendlyPieceCanCaptureCheckingPiece() {
        Coordinates coords = new Coordinates(0, 0);
        board.placePiece(coords, king);

        Piece friendlyPiece = new Rook(PlayerColour.WHITE);
        Coordinates friendlyCoords = new Coordinates(7, 7);
        board.placePiece(friendlyCoords, friendlyPiece);

        Piece opponent = new Rook(PlayerColour.BLACK);
        Coordinates opponentCoords = new Coordinates(0, 7);
        board.placePiece(opponentCoords, opponent);

        Game game = new Game(board);
        List<Move> allowedMoves = game.getAllowedMoves(friendlyCoords);

        assertThat(allowedMoves).containsExactlyInAnyOrder(new Move(friendlyCoords, opponentCoords));
    }
}
