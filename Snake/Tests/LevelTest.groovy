import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TestGenerator

/**
 * Created by Michal on 2016-06-19.
 */
class LevelTest extends groovy.util.GroovyTestCase {

    @Test
    public void Test0()
    {
        Level testeingLevel = new Level(10, 10);
        assertEquals(10, testeingLevel.getCountY());
    }
}
