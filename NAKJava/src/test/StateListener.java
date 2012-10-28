package test;
import java.util.Deque;

public interface StateListener {
	public void perform(Deque<StackState> stack);
}
