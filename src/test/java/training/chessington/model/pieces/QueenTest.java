package training.chessington.model.pieces;

import org.junit.Before;
import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class QueenTest {

    private Board board;
    private Queen queen = new Queen(PlayerColour.WHITE);

    @Before
    public void setup() {
        board = Board.empty();
    }

    @Test
    public void queenCanMoveLaterally() {
        Coordinates coords = new Coordinates(3, 4);
        board.placePiece(coords, queen);

        List<Move> allowedMoves = queen.getAllowedMoves(coords, board);

        assertThat(allowedMoves).contains(
                new Move(coords, new Coordinates(3, 0)),
                new Move(coords, new Coordinates(3, 1)),
                new Move(coords, new Coordinates(3, 2)),
                new Move(coords, new Coordinates(3, 3)),
                new Move(coords, new Coordinates(3, 5)),
                new Move(coords, new Coordinates(3, 6)),
                new Move(coords, new Coordinates(3, 7)),
                new Move(coords, new Coordinates(0, 4)),
                new Move(coords, new Coordinates(1, 4)),
                new Move(coords, new Coordinates(2, 4)),
                new Move(coords, new Coordinates(4, 4)),
                new Move(coords, new Coordinates(5, 4)),
                new Move(coords, new Coordinates(6, 4)),
                new Move(coords, new Coordinates(7, 4))
        );
    }

    @Test
    public void queenCanMoveDiagonally() {
        Coordinates coords = new Coordinates(3, 4);
        board.placePiece(coords, queen);

        List<Move> allowedMoves = queen.getAllowedMoves(coords, board);

        assertThat(allowedMoves).contains(
                new Move(coords, new Coordinates(0, 1)),
                new Move(coords, new Coordinates(1, 2)),
                new Move(coords, new Coordinates(2, 3)),
                new Move(coords, new Coordinates(4, 5)),
                new Move(coords, new Coordinates(5, 6)),
                new Move(coords, new Coordinates(6, 7)),
                new Move(coords, new Coordinates(7, 0)),
                new Move(coords, new Coordinates(6, 1)),
                new Move(coords, new Coordinates(5, 2)),
                new Move(coords, new Coordinates(4, 3)),
                new Move(coords, new Coordinates(2, 5)),
                new Move(coords, new Coordinates(1, 6)),
                new Move(coords, new Coordinates(0, 7))
        );
    }

    @Test
    public void queenOnlyAllowsDiagonalAndLateralMoves() {
        Coordinates coords = new Coordinates(3, 4);
        board.placePiece(coords, queen);

        List<Move> allowedMoves = queen.getAllowedMoves(coords, board);

        assertThat(allowedMoves).hasSize(27);
    }

    @Test
    public void queenCanCaptureOpposingPieces() {
        Coordinates coords = new Coordinates(3, 4);
        board.placePiece(coords, queen);

        Piece opponent = new Queen(PlayerColour.BLACK);
        Coordinates opponentCoords = new Coordinates(3, 5);
        board.placePiece(opponentCoords, opponent);

        List<Move> allowedMoves = queen.getAllowedMoves(coords, board);

        assertThat(allowedMoves).contains(new Move(coords, opponentCoords));
    }

    @Test
    public void queenCannotPassThroughOpposingPieces() {
        Coordinates coords = new Coordinates(3, 4);
        board.placePiece(coords, queen);

        Piece opponent = new Rook(PlayerColour.BLACK);
        Coordinates opponentCoords = new Coordinates(3, 5);
        board.placePiece(opponentCoords, opponent);

        List<Move> allowedMoves = queen.getAllowedMoves(coords, board);

        assertThat(allowedMoves).doesNotContain(
                new Move(coords, new Coordinates(3, 6)),
                new Move(coords, new Coordinates(3, 7))
        );
    }

    @Test
    public void queenisBlockedByFriendlyPieces() {
        Coordinates coords = new Coordinates(3, 4);
        board.placePiece(coords, queen);

        Piece friendlyPiece = new Rook(PlayerColour.WHITE);
        Coordinates friendlyCoords = new Coordinates(3, 5);
        board.placePiece(friendlyCoords, friendlyPiece);

        List<Move> allowedMoves = queen.getAllowedMoves(coords, board);

        assertThat(allowedMoves).doesNotContain(
                new Move(coords, new Coordinates(3, 5)),
                new Move(coords, new Coordinates(3, 6)),
                new Move(coords, new Coordinates(3, 7))
        );
    }
}
