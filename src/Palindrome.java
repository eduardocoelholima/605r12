import java.lang.StringBuilder;
import java.util.* ;


class   Result  implements Comparable<Result> {
        String output;
        int theNumber;
        Palindrome aPalindrome;

        Result(int theNumber, String output)        {
                this.theNumber    = theNumber;
                this.output       = output;
        }
	public String toString()        {
                return  output;
        }
        public int compareTo(Result aResult)    {
                return theNumber - aResult.theNumber;
        }
}
public class Palindrome extends Thread {
	static int GLOBAL_START 		= 78;
	static int GLOBAL_END	 		= 88;
	static int MINIMUM_DELAYED 	= 1;  
	static int MAXIMUM_DELAYED 	= 10; 
        static SortedStorage<Result> theResults = new SortedStorage<Result>();
	static StorageInterface<Result> theResult = new SortedStorage<Result>();
        static List<Palindrome> theThreads = new ArrayList<Palindrome>();
	int START = 0;
	int END = 0;

        int numberToTest;
        Result aResult;

        public Palindrome(int START, int END) {
                 this.START = START;
                 this.END = END;
        }

	public int  delayedPalindrome(int firstNumberofSequence, int theNumber, int delayed)	{
		int rValue = 0;

		String 	      original       = Integer.toString(theNumber);
		StringBuilder aStringBuilder = new StringBuilder(original);
		String        lanigiro       = aStringBuilder.reverse().substring(0); 
		int	      rebmuNeht      = Integer.valueOf(lanigiro);
		
		int result = Integer.valueOf(new StringBuilder(original).reverse().substring(0));
		String resultString  = Integer.toString(result);
		String resultStringReverse  = new StringBuilder(resultString).reverse().substring(0);
		rValue =  theNumber + rebmuNeht;		// might be overwritten with 0; end of search
		
		String output = "";
                boolean notAlychrelNumber = ( ( MINIMUM_DELAYED  <= delayed ) && ( delayed <= MAXIMUM_DELAYED ) &&   resultString.equals(resultStringReverse ) );
                boolean lastTry           = ( ( MINIMUM_DELAYED  <= delayed ) && ( delayed == MAXIMUM_DELAYED ) && ! resultString.equals(resultStringReverse ) );
                if  ( notAlychrelNumber || lastTry ) {
                        if  ( notAlychrelNumber )       {
				String reverseNumerFormat = "%0" + (""  + theNumber).length()   + "d";
				String numberFormat = "%10d";
				String stringFormat = "%4s";
				String delayFormat = "%4d";
				output += String.format(numberFormat,firstNumberofSequence) + ":    " ;
				output += "delayed " + String.format(delayFormat, delayed);
                                output += ":   ";
				output += String.format(numberFormat, theNumber);
				output += String.format(stringFormat, "+");
				output += String.format(numberFormat, rebmuNeht );
				output += String.format(stringFormat, "=");
				output += String.format(numberFormat, ( theNumber + rebmuNeht) ) ;
				theResults.add( new Result(firstNumberofSequence, output) );
                                rValue = 0;   //  meets requirement
                        }
                }
                return rValue;
	}

	public void determineForOneNumber(int numberToTest)       {
		int theNextnumber = 1;
		int numberToTestMeetsRequirement = 0;		// means numberToTest is meeting the requirements
	        int  delayed = 1;
		do {
			theNextnumber = delayedPalindrome(numberToTest, delayed == 1 ? numberToTest : theNextnumber, delayed);
		} while  ( ( ! ( theNextnumber == numberToTestMeetsRequirement ) ) && ( delayed++ <= MAXIMUM_DELAYED )  );
	}
	public void run()	{
		int numberToTest = START;
		while (  numberToTest <= END )	{
			determineForOneNumber(numberToTest);
			numberToTest = numberToTest + 1; 
		}
	}
	public static void setUp(int soManyThreads) {
		int delta =  ( GLOBAL_END - GLOBAL_START ) / soManyThreads ;
		int index = 0;
		int start = 0;
		int end =   0;
		while ( index < soManyThreads )	{
			start = (   ( index * delta ) ) + GLOBAL_START;
			end =   ( ( ( index + 1 ) * delta ) - 1 ) + GLOBAL_START;
			Palindrome aPalindrome = new Palindrome(start, end );
			theThreads.add(aPalindrome);
			aPalindrome.start();
			index ++;
		}
		if (  end < GLOBAL_END )	{
			start = end + 1;
			end   = GLOBAL_END;
			Palindrome aPalindrome = new Palindrome(start, end );
			theThreads.add(aPalindrome);
			aPalindrome.start();
		}
	}
	public static void waitForAll() {
		for ( int index = 0; index < theThreads.size(); index ++ ) {
			Palindrome aPalindrome = theThreads.get(index);
			try { 
				aPalindrome.join();
			} catch ( InterruptedException e )	{
				e.printStackTrace();
			}
		}
	}
	public static void printResult()	{
		// Collections.sort(theResults);
// System.out.println("printResult: " + theResults);
		List<Result> theResultsAsList = theResults.getList();
		while ( ! theResultsAsList.isEmpty() ) {
			Result aResult = theResultsAsList.get(0);
			theResultsAsList.remove(0);
		System.out.println(aResult);
		}
	}
	public static void main( String args[] ) {
		setUp(args.length > 0 ? Integer.parseInt(args[0]) : GLOBAL_END - GLOBAL_START );
		waitForAll();
		printResult();
	}
}
