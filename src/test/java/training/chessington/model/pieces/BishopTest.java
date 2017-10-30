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

public class BishopTest {

    private Board board;
    private Bishop bishop = new Bishop(PlayerColour.WHITE);

    @Before
    public void setup() {
        board = Board.empty();
    }

    @Test
    public void bishopCanMoveDiagonally() {
        Coordinates coords = new Coordinates(3, 4);
        board.placePiece(coords, bishop);

        List<Move> allowedMoves = bishop.getAllowedMoves(coords, board);

        assertThat(allowedMoves).containsExactlyInAnyOrder(
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
    public void bishopCanCaptureOpposingPiece() {
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, bishop);

        Piece opponent = new Queen(PlayerColour.BLACK);
        Coordinates opponentCoords = new Coordinates(2, 2);
        board.placePiece(opponentCoords, opponent);

        List<Move> allowedMoves = bishop.getAllowedMoves(coords, board);

        assertThat(allowedMoves).contains(new Move(coords, opponentCoords));
    }

    @Test
    public void bishopCannotMoveThroughOpposingPiece() {
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, bishop);

        Piece opponent = new Queen(PlayerColour.BLACK);
        Coordinates opponentCoords = new Coordinates(2, 2);
        board.placePiece(opponentCoords, opponent);

        List<Move> allowedMoves = bishop.getAllowedMoves(coords, board);

        assertThat(allowedMoves).doesNotContain(
                new Move(coords, new Coordinates(1, 1)),
                new Move(coords, new Coordinates(0, 0))
        );
    }

    @Test
    public void bishopIsBlockedByFriendlyPiece() {
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, bishop);

        Piece friendlyPiece = new Queen(PlayerColour.WHITE);
        Coordinates friendlyCoords = new Coordinates(2, 2);
        board.placePiece(friendlyCoords, friendlyPiece);

        List<Move> allowedMoves = bishop.getAllowedMoves(coords, board);

        assertThat(allowedMoves).doesNotContain(
                new Move(coords, friendlyCoords),
                new Move(coords, new Coordinates(1, 1)),
                new Move(coords, new Coordinates(0, 0))
        );
    }
}
