package storm.starter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class TweetRecommendation {
	String positive = "Positive";
	BufferedReader bufferedReader;
	

	public String recommendation(ClassifiedTweet classifiedTweet,String FilePath){
		StringBuilder sB = new StringBuilder();
		String finalString = new String();
		if(classifiedTweet.getClassification().equalsIgnoreCase(positive)){
			try {
				String team=classifiedTweet.getTeam();
				bufferedReader = new BufferedReader(new FileReader(FilePath + team+"+.txt"));
				String string;
				StringBuilder recommendationText = new StringBuilder();
				while((string=bufferedReader.readLine())!=null){
					recommendationText.append( "<a href='"+string + "'>"+string+"</a><br/>");
					
				}
				sB.append("<tr><td>" + classifiedTweet.getUserId() + "</td><td>"+
						classifiedTweet.getTeam()+"</td><td>"+classifiedTweet.getClassification()+
						"</td><td>"+recommendationText+"</td></tr>");
				finalString = sB.toString();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			try {
				String team=classifiedTweet.getTeam();
				bufferedReader = new BufferedReader(new FileReader(FilePath + team+"-.txt"));
				String string;
				StringBuilder recommendationText = new StringBuilder();
				while((string=bufferedReader.readLine())!=null){
					recommendationText.append( "<a href='"+string + "'>"+string+"</a><br/>");
					
				}
				sB.append("<tr><td>" + classifiedTweet.getUserId() + "</td><td>"+
						classifiedTweet.getTeam()+"</td><td>"+classifiedTweet.getClassification()+
						"</td><td>"+recommendationText+"</td></tr>");
				finalString = sB.toString();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return finalString;
	}
}
