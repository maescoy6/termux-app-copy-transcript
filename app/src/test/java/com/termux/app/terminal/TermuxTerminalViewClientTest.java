package com.termux.app.terminal;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TermuxTerminalViewClientTest {

    private static String invokePrepareTranscript(String transcriptText) {
        try {
            Method method = TermuxTerminalViewClient.class.getDeclaredMethod(
                "prepareSessionTranscriptTextForTransfer", String.class);
            method.setAccessible(true);
            return (String) method.invoke(null, transcriptText);
        } catch (NoSuchMethodException e) {
            Assert.fail("prepareSessionTranscriptTextForTransfer is not implemented");
            return null;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }

    private static String repeat(char value, int count) {
        char[] chars = new char[count];
        Arrays.fill(chars, value);
        return new String(chars);
    }

    @Test
    public void testPrepareSessionTranscriptTextForTransferKeepsNewestLinesWhenOversized() {
        String recentText = "RECENT-LINE-1\nRECENT-LINE-2";
        String transcriptText = repeat('A', 102400) + "\n" + recentText;

        Assert.assertEquals(recentText, invokePrepareTranscript(transcriptText));
    }
}
