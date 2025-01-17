package org.firstinspires.ftc.teamcode.auto.instruct;

import static org.firstinspires.ftc.teamcode.auto.instruct.AutoInstructionConstants.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AutoInstructionTextSerializer {


    private static String getInput(String prompt) {
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        System.out.print(prompt);
        return sc.nextLine();
    }


    // This is to convert text file instructions back to code instructions

    // READ THIS TO RUN THIS CODE:
    // TO RUN, CLICK THE GREEN TRIANGLE NEXT TO THE METHOD DECLARATION LINE,
    // THEN CLICK "RUN "AutoInstructionTextSerializer" *WITH COVERAGE*.

    // To USE the program, you must enter a file path in the console, which can be accessed through
    // "Cover" at the bottom. You paste the file path ON YOUR COMPUTER and it will paste the code between
    // two lines of text: "BEGIN WRITING" and "END WRITING". You will most likely have to scroll up to see it.

    // Be sure to SET THE RUN CONFIGURATION BACK TO TEAMCODE WHEN REUPLOADING THE ACTUAL CODE.
    public static void main(String[] args) throws IOException {


        StringBuilder builtInstructions = new StringBuilder("");
        AutoInstructionReader reader = new AutoInstructionReader(getInput("Input path of file:"));

        System.out.println("\n\n\nBEGIN WRITING\n\n\n");

        AutoOperation autoOperation;
        boolean storeAsync = false, creatingLinearRunnable = false;
        autoIterator:
        while ((autoOperation = reader.readLine()) != null) {


            final String operationName = autoOperation.getOperationName();
            final ArrayList<String> operationArgs = autoOperation.getOperationArgs();

            ArrayList<String> currBuiltInstruction = new ArrayList<>();


            boolean skipCurrOperation = false;
            switch (operationName) {

                // It intentionally appends STOP anyway so that when the user pastes the code, they will notice
                // the error and do whatever they will to fix it (maybe they forgot to remove it after testing
                // certain parts of autonomous after converting text to code)
                case singleCommentMarker:
                    builtInstructions.append("\t" + (storeAsync ? "\t" : "") + (creatingLinearRunnable ? "\t" : ""));
                    skipCurrOperation = true;
                    break;
                case multiCommentBegin:
                case multiCommentEnd:
                case stopMarker:
//                    currBuiltInstruction.add(operationName);
//                    skipCurrOperation = true;
                    System.out.println("-------- Encountered STOP operator --------");
                    break autoIterator;
                case multiThreadingMarker:
                    builtInstructions.append("runTasksAsync(");
                    builtInstructions.append("\n");
                    storeAsync = true;
                    continue;
                case endThreadingMarker:
                    builtInstructions.append(");");
                    builtInstructions.append("\n");
                    storeAsync = false;
                    continue;
                case linearRunnableMarker:
                    builtInstructions.append("() -> {");
                    builtInstructions.append("\n");
                    creatingLinearRunnable = true;
                    continue;
                case endLinearRunnableMarker:
                    builtInstructions.append("},");
                    builtInstructions.append("\n");
                    creatingLinearRunnable = false;
                    continue;

            }
            if (skipCurrOperation) {
                currBuiltInstruction.add(joinArgsText(operationArgs));
                builtInstructions.append(joinArgsText(currBuiltInstruction));
                builtInstructions.append("\n");
                continue;
            }
            String hereOperation = "";
            switch (operationName) {
                case "setPower":
                case "setPosition":
                    hereOperation = operationArgs.get(0) + period + operationName + openParenthesis + operationArgs.get(1) + closeParenthesis;
                    break;
                case "powerFactor":
                    hereOperation = "this." + operationName + " = " + operationArgs.get(0);
                    break;
                case "":
                    continue;
                default:
                    hereOperation = operationName + openParenthesis + joinArgsCode(operationArgs) + closeParenthesis;
                    break;
            }
            builtInstructions.append(((storeAsync && !creatingLinearRunnable) ? "() -> " : "") + hereOperation + ((storeAsync && !creatingLinearRunnable) ? "," : semicolon));
//            builtInstructions.append(joinArgsText(operationArgs));
            builtInstructions.append("\n");


        }
        reader.close();


        System.out.println(builtInstructions.toString());
        System.out.println("\n\n\nEND WRITING\n\n\n");

        main(args);
    }
}