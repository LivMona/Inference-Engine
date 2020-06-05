/********************************
 *                              *
 *      Inference Engine        *
 *  @author Olivia Monacella    *
 *                              *
 *******************************/

package inferenceengine;
import java.util.*;

public class TruthTable extends Methods 
{
// Public Methods
    public TruthTable() // Constructor for Truth Table
    {
        //initialise variables
        methodName = "Truth Table";
        methodCode = "TT";
        kb = new HashSet<>();
        finalSol = new StringBuilder();
        numVal = 0;
        
        if(testMode)
        {
            testTries = 0;
        }
    }
    
    @Override
    public String Solve(String kb, String query) // Solve method
    {
        //initialise variables
        HashSet<String> sym = new HashSet<>();
        HashMap<String, Boolean> sbTemp = new HashMap<>();
        sym = InitaliseAll(kb, sym);
        CheckAll(query, sym, sbTemp);
        
        /***********************Test Mode*****************************/
            if (testMode)                                            //
            {                                                        //
                System.out.println("Attempts Needed: " + testTries); //
            }                                                        //
        /****************Report amount of tries needed****************/
        
        
        if(numVal > 0) // If any models are found return results
        {
            return numVal.toString();
        }
        else
        {
            return null;
        }
    }
    
// Private Methods
    // Initate Methods
    private HashSet<String> InitaliseAll(String kb, HashSet<String> sym) 
    {
        InitaliseKB(kb);
        return InitaliseSymbols(sym);
    }
    
    private void InitaliseKB(String kb) // Initalise KB 
    {
        kb = kb.replaceAll("\\s", "");
        String[] sArray = kb.split(";");
        for (String temp : sArray) 
        {
            if (!temp.contains("=>")) 
            {
                this.kb.add(new HornClause(temp.trim(), null));

            } 
            else 
            {
                String[] splitArray = temp.split("=>");
                this.kb.add(new HornClause(splitArray[0].trim(), splitArray[1].trim()));
            }
        }
    }
    
    private HashSet<String> InitaliseSymbols(HashSet<String> sym) // Create HashSet of sym
    {
        this.kb.stream().map((temp) -> 
        {
            AddSymbol(temp.getBefore(), sym);
            return temp;
        }).forEachOrdered((temp) -> 
        {
            AddSymbol(temp.getAfter(), sym);
        });
        return sym;
    }
    
    // Alter Methods
    private void AddSymbol(String sym, HashSet<String> symList) // Add sym to symList HashSet
    {
        if (sym != null) 
        {
            if (sym.contains("&")) 
            {
                String[] splitSymbols = sym.split("&");
                symList.addAll(Arrays.asList(splitSymbols));
            }
            else 
            {
                symList.add(sym);
            }
        }
    }
    
    private HashMap<String, Boolean> AddToHash(HashMap<String, Boolean> symList, String sym, Boolean check) // Add sym to symList HashMap
    {
        symList.put(sym, check);
        return symList;
    }
    
    // Check Methods
    private boolean CheckAll(String search, HashSet<String> sym, HashMap<String, Boolean> symList) // Check all possible options
    {
        if (!sym.isEmpty()) 
        {
            /************Test Mode************/
                if (testMode)                //
                {                            //
                    testTries++;             //
                }                            //
            /***Calculate tries to complete***/
            
            HashSet<String> under = new HashSet<>(sym);
            String popTemp = sym.iterator().next();
            under.remove(popTemp); // pop top
            return CheckAll(search, under, AddToHash(symList, popTemp, true)) && CheckAll(search, under, AddToHash(symList, popTemp, false));            
        } 
        else 
        {
            if (CheckKB(symList)) 
            {
                if (symList.get(search)) 
                {
                    numVal++;
                    return true;
                } 
                else 
                {
                    return false;
                }
            } 
            else 
            {
                return true;
            }
        }
    }
    
    private boolean CheckKB(HashMap<String, Boolean> symList) // Check every entry in kb to see if they are correct
    {
        boolean result = true;
        
        for (HornClause comp : this.kb) {
            if (CheckKBTrue(symList, comp) == false) 
            {
                result = false;
                break;
            }
        }
        return result;
    }
    
    private boolean CheckKBTrue(HashMap<String, Boolean> symList, HornClause comp ) // Check if kb is true
    {
        if (comp.getAfter() == null)
        {
            return symList.get(comp.getBefore());
        }
        switch(comp.getBeforeSym().size())
        {
            case 1: 
                if(symList.get(comp.getBefore()) != false)
                {
                    return symList.get(comp.getAfter());
                }
                break;
            case 2:
                if ((symList.get(comp.getBeforeSym().iterator().next()) && symList.get(comp.getBeforeSym().iterator().next())) != false) 
                {
                    return symList.get(comp.getAfter());
                }
                break;
        }
        return true;
    }
}

