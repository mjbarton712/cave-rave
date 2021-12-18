
/**
 * Write a description of class test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class test
{
    public static void main()
    {
          System.out.println(AddSpell());
    }
    public static String AddSpell()
        {
            String potion1 = "apple";
            String potion2 = "banana";
            
            String newPotion = "";
            int random = 0;
            while (potion1.length() != 0 || potion2.length() != 0)
            {
                random = (int)(Math.random()*2);
                
               
                if (random == 1 && potion1.length() > 0)
                {
                    newPotion += potion1.substring(0,1);
                    potion1 = potion1.substring(1);
                }
                else if (potion2.length() > 0)
                {
                    newPotion += potion2.substring(0, 1);
                    potion2 = potion2.substring(1);
                }
                else
                {
                    newPotion += potion1.substring(0, 1);
                    potion1 = potion1.substring(1);
                }

            }
            return newPotion;
        }
}
