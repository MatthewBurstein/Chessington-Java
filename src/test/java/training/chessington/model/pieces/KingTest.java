package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class KingTest {

    @Test
    public void kingCanMoveToAdjacentSquares() {
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, king);

        List<Move> allowedMoves = king.getAllowedMoves(coords, board);

        assertThat(allowedMoves).containsExactlyInAnyOrder(
                new Move(coords, coords.plus(1, 1)),
                new Move(coords, coords.plus(1, 0)),
                new Move(coords, coords.plus(1, -1)),
                new Move(coords, coords.plus(0, 1)),
                new Move(coords, coords.plus(0, -1)),
                new Move(coords, coords.plus(-1, 1)),
                new Move(coords, coords.plus(-1, 0)),
                new Move(coords, coords.plus(-1, -1))
        );
    }

    @Test
    public void kingCanTakeOpposingPiece() {
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, king);

        Piece opponent = new Pawn(PlayerColour.BLACK);
        Coordinates opponentCoords = coords.plus(1, 0);
        board.placePiece(opponentCoords, opponent);

        List<Move> allowedMoves = king.getAllowedMoves(coords, board);

        assertThat(allowedMoves).contains(new Move(coords, opponentCoords));
    }

    @Test
    public void kingCannotTakeFriendlyPiece() {
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, king);

        Piece friendlyPiece = new Knight(PlayerColour.WHITE);
        Coordinates friendlyCoords = coords.plus(1, 0);
        board.placePiece(friendlyCoords, friendlyPiece);

        List<Move> allowedMoves = king.getAllowedMoves(coords, board);

        assertThat(allowedMoves).doesNotContain(new Move(coords, friendlyCoords));
    }

    @Test
    public void kingCannotLeaveBoard() {
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(0, 0);
        board.placePiece(coords, king);

        List<Move> allowedMoves = king.getAllowedMoves(coords, board);

        assertThat(allowedMoves).containsExactlyInAnyOrder(
                new Move(coords, new Coordinates(0, 1)),
                new Move(coords, new Coordinates(1, 1)),
                new Move(coords, new Coordinates(1, 0))
        );
    }
}
