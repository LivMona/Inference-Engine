/********************************
 *                              *
 *      Inference Engine        *
 *  @author Olivia Monacella    *
 *                              *
 *******************************/

package inferenceengine;
import java.util.*;

public abstract class Methods 
{
    public String methodCode;
    public String methodName;
    public StringBuilder finalSol;
    public Integer numVal;
    public HashSet<HornClause> kb;
    public HashMap<String, Integer> amt;
    public HashMap<String, Boolean> steps;
    public LinkedList<String> toDo;
    public LinkedList<String> involve;
    
    public abstract String Solve(String kb, String query);
    
    /******* Used for testing mode *******/
        public Boolean testMode = false; //
    //      true = On || false = off     //
        public Integer testTries;        //
    /*************************************/
}
