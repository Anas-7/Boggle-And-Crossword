package com.example.mohdma.daa_final;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Boggle{
	static ArrayList<String> finalwords = new ArrayList<String>();
	static FileInputStream file;

    public Boggle(FileInputStream file){
	    this.file=file;
    }

    public static ArrayList<String> search(char[][] board)
    {
        
		//char[][] board  = {{'G','I','Z' },{'U','E','K'},{'Q','S','E'}};
        finalwords.clear();
        boolean[][] visited = {{false,false,false },{false,false,false},{false,false,false}};
        String word = "";
        ArrayList<String> english = new ArrayList<String>();
		try
        {
            ObjectInputStream ois=null;
            int bufferSize = 16 * 1024;
            ois = new ObjectInputStream(new BufferedInputStream(file, bufferSize));
            english = castToAnything(ois.readObject());
            ois.close();
            file.close();
         }catch(IOException ioe){
             ioe.printStackTrace();
             return null;
          }
		  catch(ClassNotFoundException c){
             System.out.println("Class not found");
             c.printStackTrace();
             return null;
          }

		int limit_flag=0;
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                visited[row][col] = true;
                try {
                    limit_flag = FindWord(board, visited, row, col, word + board[row][col], english);
                }catch (RuntimeException e){
                    return finalwords;
                }
                visited[row][col] = false;
            }
        }
        System.out.println(finalwords);
		return finalwords;
    }
	
	@SuppressWarnings("unchecked")
	public static <String> String castToAnything(Object obj) {
		return (String) obj;
	}

    static int[] pathRow = { 0, 0, 1, 1, -1, 1, -1, -1 };
    static int[] pathCol = { 1, -1, -1, 1, 1, 0, 0, -1 };

    public static int FindWord(char[][] board, boolean[][] visited, int row, int col, String word, List<String> english) throws RuntimeException
    {
        if (english.contains(word) && !finalwords.contains(word))
        {
			finalwords.add(word);
			finalwords.trimToSize();
			throw new RuntimeException();
        }
        if (word.length()==9)
        {
            return 0;
        }
        for (int i = 0; i < pathRow.length; i++)
        {
            int rowNew = row + pathRow[i];
            int colNew = col + pathCol[i];
            if (IFValid(rowNew, colNew, visited))
            {
                visited[rowNew][colNew] = true;
                FindWord(board, visited, rowNew, colNew, word + board[rowNew][colNew], english);
                visited[rowNew][colNew] = false;
            }
        }
        return 0;
    }

    private static boolean IFValid(int rowNew, int colNew, boolean[][] visited)
    {
        if (rowNew >= 0 && colNew >= 0 && rowNew < 3 && colNew < 3 && visited[rowNew][colNew]==false)
        {
            return true;
        }
           else
        {
            return false;
        }
    }
}
