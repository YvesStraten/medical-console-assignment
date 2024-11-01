package com.yvesstraten.medicalconsole;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MedicalConsole {
	public enum ConsoleOption {
		ADD(1), 
		LIST(2), 
		DELETE(3), 
		SIMULATE(4), 
		OPERATE(5), 
		QUIT(6);

		private final int value;

		private ConsoleOption(int value) {
			this.value = value;
		}

		public int getValue(){
			return this.value;
		}
	}
	  public static void printIntroduction(){
		String[] options = {
			new String("Add a service"), 
			new String("List all services"), 
			new String("Delete a service"),
			new String("Simulate patient visit"),
			new String("Operate"),
			new String("Quit")

		};
		  System.out.println("Welcome to HELP Medical console");
		  System.out.println("What would you like to do today?");
		  System.out.println();

		for(int i = 0; i < options.length; i++){
			System.out.printf("[%d] %s \n", i + 1, options[i]);
		}

	}

	public static void addObject(HealthService service){
		System.out.println("Which service would you like to add?");

	}

	public static void executeOption(ConsoleOption selectedOption, HealthService service){
		switch(selectedOption){
			case QUIT: 
				System.exit(0);
			case ADD: 
			  addObject(service); 
			  break;
		}

	}

	public static boolean checkOption(int inputOption) throws InvalidOptionException {
				ConsoleOption[] optionValues = ConsoleOption.values();

					if(inputOption == optionValues[inputOption - 1].getValue()){
				   return true;
					}

				
					throw new InvalidOptionException(String.format("This option is invalid, please input a number from [%d-%d]", 1, optionValues.length));

	}
    public static void main(String[] args) {
		  HealthService service = new HealthService();
		  Scanner stdin = new Scanner(System.in);
		  boolean quit = false;

			do {
			printIntroduction();
			System.out.print("Select an option: ");
			try {
			  int inputOption = stdin.nextInt();
				boolean validOption = checkOption(inputOption);

				if(validOption){
					executeOption(ConsoleOption.values()[inputOption - 1], service);
				}
			} catch(InputMismatchException exception) {
				System.err.println("Wrong option, please try again!");
			} catch(InvalidOptionException exception){
				System.err.println(exception.getMessage() + "\n");
			}



		} while(true);
    }
}
