package pic16f84sim.microcontroller.memory;

import io.reactivex.subjects.PublishSubject;

public class Stack {

    private final short[] stack;

    private final PublishSubject<short[]> stackSubject;
    private final PublishSubject<Integer> stackPointerSubject;

    private int stackPointer;

    public Stack(PublishSubject<short[]> sSubject, PublishSubject<Integer> spSubject) {
        stackSubject = sSubject;
        stackPointerSubject = spSubject;

        stack = new short[8];
    }

    public void push(short address) {
        stack[stackPointer] = address;
        stackPointer++;

        if(stackPointer == 8) {
            stackPointer = 0;
        }

        notifyChangeInStack();
    }

    public short pop() {
        if(stackPointer == 0) {
            stackPointer = 7;
        } else {
            stackPointer--;
        }

        notifyChangeInStack();
        return stack[stackPointer];
    }

    public void resetStackPointer() {
        stackPointer = 0;
        notifyChangeInStack();
    }

    private void notifyChangeInStack() {
        stackSubject.onNext(stack);
        stackPointerSubject.onNext(stackPointer);
    }
}
