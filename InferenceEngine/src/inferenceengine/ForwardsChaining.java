/********************************
 *                              *
 *      Inference Engine        *
 *  @author Olivia Monacella    *
 *                              *
 *******************************/


package inferenceengine;
import java.util.*;

public class ForwardsChaining extends Methods 
{
// Public Methods
    public ForwardsChaining()  // Constructor
    {
        // Initalise variables
        methodCode = "FC";
        methodName = "Forward Chaining";
        amt = new HashMap<>();
        steps = new HashMap<>();
        toDo = new LinkedList<>();
        finalSol = new StringBuilder();
        
        // If testing mode is enabled, initalise testTries
        if(testMode)
        {
            testTries = 0;
        }
    }
    
    @Override
    public String Solve(String kb, String search) // Solve method
    {
        InitaliseKB(kb); // Create enviroment and initalize kb

        // Search all options
        while (toDo.isEmpty() != true) 
        {
            /************Test Mode************/
                if (testMode)                //
                {                            //
                    testTries++;             //
                }                            //
            /***Calculate tries to complete***/
            
            
            String pop = toDo.pop(); // Take first option

            finalSol.append(pop);
            finalSol.append(", ");

            steps.put(pop, false);

            if (steps.get(pop) == false) 
            {
                steps.replace(pop, true);

                //for each Horn temp j in whose split pop appears do
               
                for (var i : amt.keySet()) 
                {
                    if (Contains(i, pop)) 
                    {
                        int temp = amt.get(i) - 1;
                        amt.replace(i, temp);

                        if (amt.get(i) == 0) 
                        {
                            //get the top of the temp
                            String top = i.split("=>")[1];

                            if (top.equals(search)) 
                            {
                                // Solution found
                            /***********************Test Mode*****************************/
                                if (testMode)                                            //
                                {                                                        //
                                    System.out.println("Success.");                      //
                                    System.out.println("Attempts Needed: " + testTries); //
                                }                                                        //
                            /****************Report amount of tries needed****************/
                            
                            
                                StringBuilder complete = finalSol.append(search);
                                return complete.toString();
                            } 
                            else 
                            {
                                //keep searching by adding it to the stack
                                toDo.add(top);
                            }
                        }
                    }
                }
            }
        }
        
        // Exhausted options = return null 
        /***********************Test Mode*****************************/
            if (testMode)                                            //
            {                                                        //
                System.out.println("Failed.");                       //
                System.out.println("Attempts Needed: " + testTries); //
            }                                                        //
        /****************Report amount of tries needed****************/
                
        return null;
    }


// Private Methods
    private void InitaliseKB(String kb) // Initalise knowlegde base
    {
        //seperate sKb
        kb = kb.replaceAll("\\s", "");
        String[] sKb = kb.split(";");
        
        for(var i = 0; i < sKb.length; i++)
        {
            if (sKb[i].contains("=>")) 
            {
                String[] splitArray = sKb[i].split("=>");
                amt.put(sKb[i].trim(), sKb[i].split("&").length);
                for(var j = 0; j < splitArray.length; j++)
                {
                    if (splitArray[j].contains("&")) 
                    {
                        String[] syms = splitArray[j].split("&");
                        
                        for(var k = 0; k < syms.length; k++)
                        {
                            steps.put(syms[k].trim(), false);
                        }
                    }
                    else 
                    {
                        steps.put(sKb[i].trim(), false);
                    }
                }
            } 
            else 
            {
                toDo.add(sKb[i].trim());
                steps.put(sKb[i].trim(), false);
            }
        }
    }
    
    
    private boolean Contains(String temp, String search) // checks temp string to see if it's search
    {
        temp = temp.split("=>")[0];
        String[] searchArray = temp.split("&");

        if (searchArray.length == 1) 
        {
            return temp.equals(search);
        } 
        else 
        {
            return Arrays.asList(searchArray).contains(search);
        }
    }
}
