package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptionsParserTest {

    @Test
    public void testParseCorrectData(){
        String[] in = {"f","f","r","r","b","b","l","l"};
        MoveDirection[] out = {MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.LEFT};
        assertArrayEquals(out, OptionsParser.Parse(in).toArray());
    }

    @Test
    public void testParseIncorrectData(){
        String[] in = {"g","g","asdasd","bbb","fff","ww","dada","zzz","1","."};
        assertThrows(IllegalArgumentException.class, () -> OptionsParser.Parse(in).toArray());
    }

    @Test
    public void testParseEmptyData(){
        String[] in = {};
        MoveDirection[] out = {};
        assertArrayEquals(out, OptionsParser.Parse(in).toArray());
    }

    @Test
    public void testParseMixedData(){
        String[] in = {"start","f","aaaaaa","f","9999","10000","b","abcd","r","r","end","stop","exit","l","close"};
        assertThrows(IllegalArgumentException.class, () -> OptionsParser.Parse(in));
    }
}
