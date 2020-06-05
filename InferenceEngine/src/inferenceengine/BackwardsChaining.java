/********************************
 *                              *
 *      Inference Engine        *
 *  @author Olivia Monacella    *
 *                              *
 *******************************/

package inferenceengine;
import java.util.*;

public class BackwardsChaining extends Methods 
{
// Public Methods
    public BackwardsChaining() // Constructor
    {
        //initialise variables
        methodCode = "BC";
        methodName = "Backwards Chaining";
        amt = new HashMap<>();
        steps = new HashMap<>();
        toDo = new LinkedList<>();
        finalSol = new StringBuilder();
        involve = new LinkedList<>();
        kb = new HashSet<>();

        // If testing mode is enabled, initalise testTries
        if (testMode) 
        {
            testTries = 0;
        }
    }

    @Override
    public String Solve(String kb, String query) // Solve method
    {
        InitaliseKB(kb);
        toDo.add(query.trim());

        while (toDo.isEmpty() != true) 
        {
            /************Test Mode************/
                if (testMode)                //
                {                            //
                    testTries++;             //
                }                            //
            /***Calculate tries to complete***/
            
            var top = toDo.pop();

            involve.add(top.trim());

            for (var temp : this.kb) 
            {
                if (temp.getBefore().equals(top))
                {
                    if(temp.getAfter() == null)
                    {
                        /***********************Test Mode*****************************/
                            if (testMode)                                            //
                            {                                                        //
                                System.out.println("Success.");                      //
                                System.out.println("Attempts Needed: " + testTries); //
                            }                                                        //
                        /****************Report amount of tries needed****************/
                        return FinalSolution();
                    }
                }
            }
            
            HashSet<String> lSym = new HashSet<>();

            this.kb.stream().filter((temp) -> (temp.getAfter() != null && temp.getAfter().equals(top))).forEachOrdered((clause) -> 
            {
                clause.getBeforeSym().forEach((temp) -> 
                {
                    lSym.add(temp);
                });
            }); 

            
            if (lSym.isEmpty() != true) 
            {
                lSym.stream().filter((s) -> (!involve.contains(s))).forEachOrdered((s) -> 
                {
                    toDo.add(s);
                }); 
            } 
            else 
            {
                return null;
                
            }
        }
        /***********************Test Mode*****************************/
            if (testMode)                                            //
            {                                                        //
                System.out.println("Failed.");                       //
                System.out.println("Attempts Needed: " + testTries); //
            }                                                        //
        /****************Report amount of tries needed****************/
        
        return FinalSolution();
    }
    
// Private Methods
    private void InitaliseKB(String kb) // Inialise Knowledge Base function
    {
        kb = kb.replaceAll("\\s", "");
        String[] sKb = kb.split(";");

        for (var i = 0; i < sKb.length; i++) 
        {
            if (sKb[i].contains("=>")) 
            {
                String[] splitArray = sKb[i].split("=>");
                this.kb.add(new HornClause(splitArray[0].trim(), splitArray[1].trim()));
            } 
            else 
            {
                this.kb.add(new HornClause(sKb[i].trim(), null));
            }
        }
    }
    
    private String FinalSolution() // Calculate final solution
    {
        for (var i = involve.size() - 1; i > -1; i--) 
        {
            finalSol.append(involve.get(i) + ", ");
        }
        
        return finalSol.deleteCharAt(finalSol.length() - 2).toString().trim();
    }
}
