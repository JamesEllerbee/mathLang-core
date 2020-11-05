package xyz.mathapp;

public interface CommandInterface
{
    void performAction(String param, String sessionId);

    void performAction(String param, CoreManager cm);
}
