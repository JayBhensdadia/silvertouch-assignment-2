import java.io.*;
class Main
{
static int MAX = 100;


static int lcslen = 0; 


static int[][] dp = new int[MAX][MAX]; 



static int lcs(String str1, String str2, 
				int len1, int len2, int i, int j) 
{ 
	int ret = dp[i][j]; 

	
	if (i == len1 || j == len2) 
	return ret = 0; 

	
	if (ret != -1) 
	return ret;	 
	ret = 0; 

	
	
	
	if (str1.charAt(i) == str2.charAt(j)) 
	ret = 1 + lcs(str1, str2, len1, len2, i + 1, j + 1); 
	else
	ret = Math.max(lcs(str1, str2, len1, len2, i + 1, j), 
					lcs(str1, str2, len1, len2, i, j + 1)); 
	return dp[i][j]=ret; 
} 



static void printAll(String str1, String str2, int len1, int len2, 
					char[] data, int indx1, int indx2, int currlcs) 
{ 

	
	if (currlcs == lcslen) 
	{ 
	data[currlcs] = '\0'; 
	System.out.println(new String(data)); 
	return; 
	} 

	
	if (indx1 == len1 || indx2 == len2) 
	return; 

	
	
	
	
	for (char ch ='a'; ch <='z'; ch++) 
	{ 

	
	
	boolean done = false; 

	for (int i = indx1; i < len1; i++) 
	{ 

		
		
		if (ch == str1.charAt(i)) 
		{ 
		for (int j = indx2; j < len2; j++) 
		{

			
			
			
			if (ch == str2.charAt(j) && 
				dp[i][j] == lcslen - currlcs) 
			{ 
			data[currlcs] = ch; 
			printAll(str1, str2, len1, len2, 
					data, i + 1, j + 1, currlcs + 1); 
			done = true; 
			break; 
			} 
		} 
		} 

		
		if (done) 
		break; 
	} 
	} 
} 



static void prinlAllLCSSorted(String str1, String str2) 
{ 

	
	int len1 = str1.length(), len2 = str2.length(); 

	
	for(int i = 0; i < MAX; i++)
	{
	for(int j = 0; j < MAX; j++)
	{
		dp[i][j] = -1;
	}
	}
	lcslen = lcs(str1, str2, len1, len2, 0, 0); 

	
	
	char[] data = new char[MAX]; 
	printAll(str1, str2, len1, len2, data, 0, 0, 0); 
} 


public static void main(String[] args) 
{
	String str1 = "abcabcaa", str2 = "acbacba"; 
	prinlAllLCSSorted(str1, str2); 
}
}


