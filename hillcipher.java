// Cullen Bair
// cu326578
// CIS 3360

import java.io.*;
import java.util.*;

public class hillcipher 
{
    // Method that prints out my bodies of text, character by character
    public static void print(ArrayList<Character> list)
    {
        int k = 0;
        // Split up the lines so that 80 characters get on each line
        for(int i = 0; i < (list.size()/80)+1; i++)
        {
            while(k < list.size())
            {
                System.out.print(list.get(k++));
                if(k % 80 == 0)
                    break;
            }
            System.out.println();
        }
    }
    
    // Method to encript my plaintext into ciphertext using Hill Cipher
    public static ArrayList<Character> encript(ArrayList<Character> list, int []key, int keysize)
    {
        // Padding the end with x's if necessary
        if(list.size() % keysize != 0)
        {
            list.add('x');
            return encript(list, key, keysize);
        }
        // Printing out the padded ciphertext 
        print(list);
        
        int k = 0, p = 0, num;
        ArrayList<Integer> tlist = new ArrayList<>();
        
        // This series of lists goes through each chunk of the plaintext and encripts it
        // with Hill Cipher, one chunk at a time based on how large the keysize is
        for(int i = 0; i < list.size()/keysize; i++)
        {
            tlist.clear();
            for(int j = 0; j < keysize; j++)
            {
                tlist.add(((int)list.get(k++))-97);
            }
            for(int j = 0; j < keysize; j++)
            {
                num = 0;
                for(int r = 0; r < keysize; r++)
                {
                    num += tlist.get(r) * key[r+(keysize*j)];
                }
                list.set(p++, (char)((num%26)+97));
            }
        }
        
        return list;
    }

    public static void main(String[] args) throws Exception
    {
        
        if(args.length < 2)
            System.err.println("Please use 2 arguments..");
        
        int key[] = new int[81];
        ArrayList<Character> text = new ArrayList<>();
        char []word;
        
        // Scanning our first argument (the key file)
        Scanner in1 = new Scanner(new File(args[0]));
        
        // Obtaining the keysize right off the bat because it is the first integer
        int keysize = Integer.parseInt(in1.next());
        
        int j = 0;
        // Filling up our key array
        while(in1.hasNext())
        {
            key[j++] = Integer.parseInt(in1.next());
        }
        
        // Printing out our key matrix to the screen for the visual
        System.out.println("\nKey matrix:");
        j = 0;
        for(int i = 0; i < keysize; i++)
        {
            for(int k = 0; k < keysize; k++)
            {
                System.out.print(key[j++] + " ");
            }
            System.out.println();
        }
        
        System.out.println("\nPlaintext:");
        
        // Our second argument will be the plaintext
        Scanner in2 = new Scanner(new File(args[1]));
        
        // This turns the plaintext into an ArrayList of lowercase characters that
        // doesn't include any special characters or spaces or anything
        while(in2.hasNext())
        {
            word = in2.next().toLowerCase().toCharArray();
            for(int i = 0; i < word.length; i++)
            {
                if(word[i] > 96 && word[i] < 123)
                    text.add(word[i]);
            }
        }
        
        // Encript and print..
        text = encript(text, key, keysize);
        
        System.out.println("\nCiphertext:");
        
        print(text);
    }
    
}
