package tests;

import be.ac.ucl.info.javagrading.GradingListener;
import org.junit.runner.JUnitCore;


public class RunTests {
    public static void main(String args[]) {
        JUnitCore runner = new JUnitCore();
        runner.addListener(new GradingListener());
			runner.run( LPComplexityTests.class, LPHackAttackTests.class, LPStaticTests.class);
    }
}