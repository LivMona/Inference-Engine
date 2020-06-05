/********************************
 *                              *
 *      Inference Engine        *
 *  @author Olivia Monacella    *
 *                              *
 *******************************/

package inferenceengine;
import java.util.*;
//
//import java.util.Arrays;
//import java.util.HashSet;

public class HornClause 
{
    // Declare Variables
    private String before;
    private String after;
    private HashSet<String> beforeSym;
    private HashSet<String> afterSym;

    
    public HornClause(String uBefore, String uAfter) // HornClause Constructor
    {
        before = uBefore;
        after = uAfter;
        beforeSym = new HashSet<>();
        afterSym = new HashSet<>();

        InitialiseSymbol(before, beforeSym);
        InitialiseSymbol(after, afterSym);
    }

    // Getters
    public String getBefore() 
    {
        return before;
    }

    public String getAfter() 
    {
        return after;
    }

    public HashSet<String> getBeforeSym() {
        return beforeSym;
    }

    public HashSet<String> getAfterSym() {
        return afterSym;
    }
    
    
    
// Private Method
    private void InitialiseSymbol(String sym, HashSet<String> symList)
    {
        //in case of no after
        if (sym != null) 
        {
            if (sym.contains("&") == false) 
            {
                symList.add(sym);
            } 
            else 
            {
                String[] splitSymbols = sym.split("&");
                symList.addAll(Arrays.asList(splitSymbols));
            }
        }
    }

}
