/********************************
 *                              *
 *      Inference Engine        *
 *  @author Olivia Monacella    *
 *                              *
 *******************************/

package inferenceengine;
import java.io.*;

class InferenceEngine 
{
    // Used for testing - true = enabled || false = disabled.
    // If true, will use every search method available
    public static boolean testMode = false; 
    
    public static Methods[] METHODS_LIST;
    public static final int METHOD_COUNT = 3;
    
    
    public static void main(String[] args) 
    {
        // Check Provided Arguments        
        if(args.length < 2)
        {
            System.out.println("Error - Not enough argumentes provided.");
            CorrectUsage();
            System.exit(1);
        }
        
        String[] lRead = ReadFile(args[1]);
        String lMeth = args[0];
        
        
        InitateMethods();
                
        /************************************************************************/
        //                              Testing all Methods                     //
        //                            Run every method in list                  //
            if(testMode)                                                        //
            {                                                                   //
                System.out.println("Testing enabled - search all methods.");    //
                String testSolution;                                            //
                Methods testMethod;                                             //
                for(int i = 0; i < METHOD_COUNT; i++)                           //
                {                                                               //
                    if(METHODS_LIST[i].methodCode.compareTo(lMeth) != 0)        //
                    {                                                           //
                        testMethod = METHODS_LIST[i];                           //
                                                                                //
                        System.out.println(testMethod.methodName);              //  
                        testSolution = testMethod.Solve(lRead[0], lRead[1]);    //
                                                                                //
                        if (testSolution == null)                               //
                        {                                                       //
                            System.out.println("NO: Unable to find a solution");//
                        }                                                       //
                        else                                                    //  
                        {                                                       //
                            System.out.println("YES: " + testSolution);         //  
                        }                                                       //
                    }                                                           //
                }                                                               //
            }                                                                   //
        //                                                                      //
        //                                                                      //
        /************************************************************************/
        
        Methods thisMethod = null;
        
        for(int i = 0; i < METHOD_COUNT; i++)
        {
            if(METHODS_LIST[i].methodCode.compareTo(lMeth) == 0)
            {
                thisMethod = METHODS_LIST[i];
                break;
            }
        }
        if(thisMethod == null)
        {
            System.out.println("Search method identified by " + lMeth + " not implemented. Methods are case sensitive. \n Please try again.");
            CorrectUsage();
            System.exit(1);
        }
        
        System.out.println("Initializing search using the " + thisMethod.methodName + " method.");
        
        String thisSolution = thisMethod.Solve(lRead[0], lRead[1]); // Send KB and search through

        if (thisSolution == null) 
        {
            System.out.println("NO: Unable to find a solution");
        } else 
        {
            System.out.println("YES: " + thisSolution);
        }        
        
        System.exit(0);
    }
    
    private static void CorrectUsage() // provide user usage info
    {
        System.out.println("Usage: iengine <method> <filename>.");
    }
    
    private static void InitateMethods() // Add methods to array
    {
        METHODS_LIST = new Methods[METHOD_COUNT];
        METHODS_LIST[0] = new TruthTable();
        METHODS_LIST[1] = new ForwardsChaining();
        METHODS_LIST[2] = new BackwardsChaining();
    }
    
    private static String[] ReadFile(String lFile) // Read txt file
    {
        try
        {
            FileReader lRead = new FileReader(lFile);
            BufferedReader fR = new BufferedReader(lRead);

            String tempString;
            String[] tempArr = new String[2];

            fR.readLine();
            tempString = fR.readLine();
            tempArr[0] = tempString;
            fR.readLine();
            tempString = fR.readLine();
            tempArr[1] = tempString;
            
            lRead.close();
            return tempArr;
        }
        catch (FileNotFoundException error)
        {
            System.out.println("Error " + error + " - Cannot access file.");
            System.exit(1);
        }
        catch (IOException error)
        {
            System.out.println("Error " + error + " - IO error.");
            System.exit(1);
        }
        return null;
    }
}
