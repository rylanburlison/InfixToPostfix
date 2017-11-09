/* ***************************************************
 * <Rylan Burlison>
 * <the date>
 * <InfixToPostfix.java>
 *
 * <takes an input (using standard input) of an expression and converts it to
 postfix and then calculates the result using the postfix and a stack>
 *************************************************** */

import java.io.*;

public class InfixToPostfix
{
	// the main function is just a driver!
	public static void main(String[] args)
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line;

			// read input, one expression at a time
			while ((line = br.readLine()) != null)
			{
				// declare and initialize the infix and postfix queues
				// ... <-- declare and initialize the infix queue here
				Queue<Character> infix = new Queue<Character>();

				// ... <-- declare and initialize the postfix queue here
				Queue<Character> postfix = new Queue<Character>();

				// build the infix queue and display it
				for (int i=0; i<line.length(); i++)
					// ... <-- enqueue each character to the infix queue here
				 //this enqueues the character at the index "i" of the input string "line"
					infix.Enqueue(line.charAt(i));
				//  display the infix queue here
				//toString method for queues already defined in queue class
				System.out.println(infix.toString());


				// convert it to its postfix equivalent and display it
				postfix = Convert(infix);
				//display the postfix queue here
				System.out.println(postfix.toString());

				/*
				// calculate the result and display it
				System.out.println(Calculate(postfix));
				System.out.println();
				*/

			}

			br.close();
		} catch (Exception e) {}
	}

	// given an infix queue, this method converts it to a postfix queue
	public static Queue<Character> Convert(Queue<Character> infix)
	{
		//an operator stack is needed to convert from infix to postfix
		Stack<Character> opStack = new Stack<Character>();
		//a queue that will be returned
		Queue<Character> pstfix = new Queue<Character>();
		//first, read the first token in the infix queue
		Character tok;
		//read until the infix queue is empty
		while (!infix.IsEmpty())
		{
			//read the character from infix queue
			tok = infix.Dequeue();
//******************************************debug to see where the conversion stops
			//System.out.println(tok);
//******************************************************
			System.out.print(opStack.toString());
			//check to see if character is an operand
			if (IsOperand(tok))
			{
				//if it is an operand then enqueue it to the postfix queue
				pstfix.Enqueue(tok);
			}
			//check to see if character is a right parentheses
			else if (tok == ')')
			{
				//if token is a right parentheses then need to get all the data until
				//we reach the matching left parentheses
				//do this by popping and enqueueing everything in the operator stack
				//until you get to a left paren and discard
				while (opStack.Peek() != '(')
				{
					pstfix.Enqueue(opStack.Pop());
				}
				//remove and disard the left parentheses
				opStack.Pop();
			}
			//otherwise the token is an operator and we need to decide wheter to push
			//it to the operator stack or to pop it from the operator stack and enqueue
			// to postfix queue (this is based on priorities defined below)
			else
			{
				if (opStack.IsEmpty())
				{
					opStack.Push(tok);
				}
				else{
					int stackPri = StackPriority(opStack.Peek());
					//pop off any tokens with a higher priority than the one being evaluated
					while(stackPri >= InfixPriority(tok))
					{
						pstfix.Enqueue(opStack.Pop());
					}
					//once no tokens in the stack have a greater or equal priotity to the
					//one being analyzed, push it onto the stack
					opStack.Push(tok);
				}
			}
			//System.out.println(opStack.toString());
		}
		//after the infix is empty need to make sure no operators were left behind
		//in the operator stack
		while (!opStack.IsEmpty())
		{
			pstfix.Enqueue(opStack.Pop());
		}
		//then return the newly ordered queue
		return pstfix;
	}

	/*
	// given a postfix queue, this method calculates the numeric result using a stack
	public static double Calculate(Queue<Character> postfix)
	{
	}
	*/

	// given a character from an expression, this method determines whether or not it is an operand
	// it's ok to use the simple char primitive type here
	public static boolean IsOperand(char c)
	{
		if (c >= '0' && c <= '9')
			return true;
		else
			return false;
	}

	// given a character that represents an operator from an expression, this method returns its infix priority
	// it's ok to use the simple char primitive type here
	public static int InfixPriority(char c)
	{
		int infix_priority;
		switch (c)
		{
			case '(':
				infix_priority = 4;
				break;
			case '^':
				infix_priority = 3;
				break;
			case '*':
				infix_priority = 2;
				break;
			case '/':
				infix_priority = 2;
				break;
			case '+':
				infix_priority = 1;
				break;
			case '-':
				infix_priority = 1;
				break;
			default:
				infix_priority = 0;
				break;
		}
		return infix_priority;
	}

	// given a character that represents an operator from an expression, this method returns its stack priority
	// since the character comes from the stack, we use the Character object here (since the stack could be empty which would return null)
	public static int StackPriority(Character c)
	{
		int stack_priority;
		switch (c)
		{
			case '^':
				stack_priority = 2;
				break;
			case '*':
				stack_priority = 2;
				break;
			 case '/':
			 	stack_priority = 2;
				break;
			case '+':
				stack_priority = 1;
				break;
			case '-':
				stack_priority = 1;
				break;
			default:
				stack_priority = 0;
				break;
		}
		return stack_priority;
	}
}
