package com.JavaWebApplication.Model;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.pipeline.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.analysis.Analyzer;
import com.JavaWebApplication.Beans.RawData;
import com.JavaWebApplication.Beans.SearchResult;


public class DataProvider {

	public List<RawData> readData() {
		MyDb db = new MyDb();
		ResultSet rs;
		ArrayList<RawData> datas = new ArrayList<RawData>();
		try (Connection con = db.getCon()){
			Statement stm = con.createStatement();
			rs = stm.executeQuery("select * from vanban");
			while(rs.next()) {
				RawData data = new RawData();
				data.setId(rs.getString("VanbanId"));
				data.setHeading(rs.getString("heading"));
				data.setTitle(rs.getString("tittle"));
				datas.add(data);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return datas;
	}
	
	public List<RawData> getHtmlById(int id) throws IOException {
		readDoc();
		MyDb db = new MyDb();
		ResultSet rs;
		ArrayList<RawData> datas = new ArrayList<RawData>();
		try (Connection con = db.getCon()){
			Statement stm = con.createStatement();
			rs = stm.executeQuery("select * from vanban where VanbanId ='" + id + "'");
			while(rs.next()) {
				RawData data = new RawData();
				data.setId(rs.getString("VanbanId"));
				data.setHtml(rs.getString("html"));
				data.setHeading(rs.getString("heading"));
				datas.add(data);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return datas;
	}
	
	public List<RawData> getPagination(int pageNumber) {
		List<RawData> data = readData();
		List<List<RawData>> parts = chopped(data, 10);
		List<RawData> rs = parts.get(pageNumber);
		return rs;
	}
	
	public static <T> List<List<T>> chopped(List<T> list, final int L) {
	    List<List<T>> parts = new ArrayList<List<T>>();
	    final int N = list.size();
	    for (int i = 0; i < N; i += L) {
	        parts.add(new ArrayList<T>(
	            list.subList(i, Math.min(N, i + L)))
	        );
	    }
	    return parts;
	}
	
	public String getHtmlById(String searchTerm) {
		String indexPath = "E:\\tokenizeFile";
		String result = null;
		try {
			Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));
			IndexReader reader = DirectoryReader.open(indexDirectory);
			IndexSearcher searcher = new IndexSearcher(reader);

			String fieldToSearch = "VanbanId";
			// Create a TermQuery for the specified VanbanId
			TermQuery query = new TermQuery(new Term(fieldToSearch, searchTerm));

			// Search for documents with the specified VanbanId
			TopDocs topDocs = searcher.search(query, 1);

			// Retrieve and process the matching document
			if (topDocs.totalHits > 0) {
				int docId = topDocs.scoreDocs[0].doc;
				Document document = searcher.doc(docId);

				// Retrieve the "heading" field content
				result = document.get("html");
				
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<SearchResult> getDocByTerm(int pageNumber, String searchTerm) {
		String indexPath = "E:\\tokenizeFile";
		List<SearchResult> searchResults = new ArrayList<>();
		try {
			
			Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));
			IndexReader reader = DirectoryReader.open(indexDirectory);
			IndexSearcher searcher = new IndexSearcher(reader);

			// Specify the field to search (heading in this case)
			String fieldToSearch = "tittle";

			// Create a QueryParser for the specified field
			QueryParser parser = new QueryParser(fieldToSearch, new StandardAnalyzer());

			// Parse the query with the specified term
			Query query = parser.parse(searchTerm.replace(" ", "_"));

			// Search for documents
			TopDocs topDocs = searcher.search(query, 210);

			// Assuming you have topDocs as the TopDocs object obtained from the search
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			// Paginate through the specified range of results
			for (int i = 0; i < scoreDocs.length; i++) {
				int docId = scoreDocs[i].doc;
				Document document = searcher.doc(docId);
				// Create a SearchResult object and add it to the list
				SearchResult result = new SearchResult();
				result.setVanbanId(document.get("VanbanId"));
				result.setHeading(document.get("heading").replace("_", " ").replaceAll("\\s*,\\s*", ", "));
				result.setHtml(document.get("html"));
				result.setPageSize(scoreDocs.length);
				searchResults.add(result);
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<List<SearchResult>> parts = chopped(searchResults, 10);
		List<SearchResult> rs = parts.get(pageNumber);
		return rs;
	}
	
	public void testIndexDoc() {
		String indexPath = "E:\\tokenizeFile";
		 
        try
        {
            //org.apache.lucene.store.Directory instance
            Directory dir = FSDirectory.open( Paths.get(indexPath) );
             
            //analyzer with the default stop words
            Analyzer analyzer = new StandardAnalyzer();
             
            //IndexWriter Configuration
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
             
            //IndexWriter writes new index files to the directory
            IndexWriter writer = new IndexWriter(dir, iwc);
             
            //Its recursive method to iterate all files and directories
            indexDoc(writer);
 
            writer.close();
            System.out.println("Finished");
        }
        catch (IOException | SQLException e)
        {
            e.printStackTrace();
        }
	}
	
	
	public void cleanDir() {
		 String path = "E:\\tokenizeFile";

	        // Create a File object representing the directory
	        File directory = new File(path);

	        // Check if the directory exists
	        if (directory.exists() && directory.isDirectory()) {
	            // Get all files in the directory
	            File[] files = directory.listFiles();

	            // Check if files are present
	            if (files != null) {
	                // Delete each file in the directory
	                for (File file : files) {
	                    if (file.isFile()) {
	                        file.delete();
	                        System.out.println("Deleted file: " + file.getName());
	                    }
	                }
	            } else {
	                System.out.println("No files found in the directory.");
	            }
	        } else {
	            System.out.println("Directory does not exist or is not a directory.");
	        }
	}
	
    static void indexDoc(IndexWriter writer) throws IOException, SQLException
    {
		MyDb db = new MyDb();
		ResultSet rs;
		Connection con = db.getCon();
		String query = "select * from vanban";
    	String[] annotations = {"wseg"};
    	VnCoreNLP pipeline = new VnCoreNLP(annotations);
    	System.out.println("Starting indexing");
		try{
			
			Statement stm = con.createStatement();
			rs = stm.executeQuery(query);

        	
            while (rs.next()) {
            	//pre-process data
            	//data formatted: Thông_tư 19/2023/TT-BNNPTNT Danh_mục hoá_chất , chế_phẩm sinh_học
            	Annotation annotation = new Annotation(rs.getString("heading"));
            	Annotation annotation1 = new Annotation(rs.getString("tittle"));
            	pipeline.annotate(annotation);
            	pipeline.annotate(annotation1);
            	Sentence firstSen = annotation.getSentences().get(0);
            	Sentence secondSen = annotation1.getSentences().get(0);
           
            	Document document = new Document();
                document.add(new StringField("VanbanId", rs.getString("VanbanId"), Field.Store.YES));
                document.add(new StoredField("html", rs.getString("html")));
                document.add(new TextField("heading", firstSen.getWordSegmentedSentence().toString(),  Field.Store.YES));
                document.add(new TextField("tittle", secondSen.getWordSegmentedSentence().toString(),  Field.Store.YES));
             
                // Add the document to the index
                writer.addDocument(document);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}

    }
    public void readDoc() {
    	String indexPath = "E:\\tokenizeFile";
        try {
            Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));
            
            // Create an IndexReader
            IndexReader reader = DirectoryReader.open(indexDirectory);

            // Create an IndexSearcher
            IndexSearcher searcher = new IndexSearcher(reader);

            // Specify the query string
        	String[] annotations = {"wseg"};
        	VnCoreNLP pipeline = new VnCoreNLP(annotations);

            // Parse the query
            String userQueryString = "quyết định ban hành";
        	Annotation annotation = new Annotation(userQueryString);
        	pipeline.annotate(annotation);
        	Sentence firstSen = annotation.getSentences().get(0);
            QueryParser parser = new QueryParser("heading", new StandardAnalyzer());
            Query query = parser.parse(firstSen.getWordSegmentedSentence().toString());

            // Search for documents
            int maxResults = 10;
            TopDocs topDocs = searcher.search(query, maxResults);
            System.out.println("Total Results :: " + topDocs.totalHits);
            // Retrieve and process the matching documents
            
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                int docId = scoreDoc.doc;
                Document document = searcher.doc(docId);
                // Process the retrieved document
                
                // Retrieve the "heading" field content
                String headingText = document.get("heading");
                // Process the retrieved heading text
                System.out.println(document.get("VanbanId")+ " :" +" Heading Text: " + headingText.replace("_", " ").replaceAll("\\s*,\\s*", ", "));
                
            }
            // Close the IndexReader
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    public List<SearchResult> getDoc(int pageNumber, String userQueryString){
    	String indexPath = "E:\\tokenizeFile";
    	File directory = new File(indexPath);
    	 if (directory.exists() && directory.isDirectory()) {
    		 File[] files = directory.listFiles();
    		 if(files.length == 0) {
    			 return null;
    		 }
    	 }
		List<SearchResult> searchResults = new ArrayList<>();
		try {
			if (searchResults.isEmpty()) {

				Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));

				// Create an IndexReader
				IndexReader reader = DirectoryReader.open(indexDirectory);

				// Create an IndexSearcher
				IndexSearcher searcher = new IndexSearcher(reader);

				// Specify the query string
				String[] annotations = { "wseg" };
				VnCoreNLP pipeline = new VnCoreNLP(annotations);

				// Parse the query
				Annotation annotation = new Annotation(userQueryString);
				pipeline.annotate(annotation);
				Sentence firstSen = annotation.getSentences().get(0);
				QueryParser parser = new QueryParser("heading", new StandardAnalyzer());

				Query query = parser.parse(firstSen.getWordSegmentedSentence().toString());
				
				TopDocs topDocs = searcher.search(query, 640);

				// Assuming you have topDocs as the TopDocs object obtained from the search
				ScoreDoc[] scoreDocs = topDocs.scoreDocs;
				int totalHits = (int) topDocs.totalHits;
				// Paginate through the specified range of results
				for (int i = 0; i < scoreDocs.length; i++) {
					int docId = scoreDocs[i].doc;
					Document document = searcher.doc(docId);
					// Create a SearchResult object and add it to the list
					SearchResult result = new SearchResult();
					result.setVanbanId(document.get("VanbanId"));
					result.setHeading(document.get("heading").replace("_", " ").replaceAll("\\s*,\\s*", ", "));
					result.setHtml(document.get("html"));
					result.setPageSize(totalHits);
					searchResults.add(result);
				}

				reader.close();
				
				if(searchResults.isEmpty()) {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<List<SearchResult>> parts = chopped(searchResults, 10);
		List<SearchResult> rs = parts.get(pageNumber);
		return rs;
    }
    
    
	public List<SearchResult> searchDoc1(int pageNumber, String userQueryString) {
		String indexPath = "E:\\tokenizeFile";
		List<SearchResult> searchResults = new ArrayList<>();
		try {
			if (searchResults.isEmpty()) {

				Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));

				// Create an IndexReader
				IndexReader reader = DirectoryReader.open(indexDirectory);

				// Create an IndexSearcher
				IndexSearcher searcher = new IndexSearcher(reader);

				// Specify the query string
				String[] annotations = { "wseg" };
				VnCoreNLP pipeline = new VnCoreNLP(annotations);

				// Parse the query

				Annotation annotation = new Annotation(userQueryString);
				pipeline.annotate(annotation);
				Sentence firstSen = annotation.getSentences().get(0);
				QueryParser parser = new QueryParser("heading", new StandardAnalyzer());

				Query query = parser.parse(firstSen.getWordSegmentedSentence().toString());

				TopDocs topDocs = searcher.search(query, 100);

				// Assuming you have topDocs as the TopDocs object obtained from the search
				ScoreDoc[] scoreDocs = topDocs.scoreDocs;
				// Paginate through the specified range of results
				for (int i = 0; i < scoreDocs.length; i++) {
					int docId = scoreDocs[i].doc;
					Document document = searcher.doc(docId);
					// Create a SearchResult object and add it to the list
					SearchResult result = new SearchResult();
					result.setVanbanId(document.get("VanbanId"));
					result.setHeading(document.get("heading").replace("_", " ").replaceAll("\\s*,\\s*", ", "));
					result.setHtml(document.get("html"));
					result.setPageSize(scoreDocs.length);
					searchResults.add(result);
				}

				reader.close();
				
				if(searchResults.isEmpty()) {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<List<SearchResult>> parts = chopped(searchResults, 10);
		List<SearchResult> rs = parts.get(pageNumber);
		return rs;
	}
	public List<SearchResult> searchDoc(int pageNumber, String userQueryString) {
	    String indexPath = "E:\\tokenizeFile";
	    List<SearchResult> searchResults = new ArrayList<>();

	    try {
	        if (searchResults.isEmpty()) {
	            Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));
	            IndexReader reader = DirectoryReader.open(indexDirectory);
	            IndexSearcher searcher = new IndexSearcher(reader);

	            String[] annotations = { "wseg" };
	            VnCoreNLP pipeline = new VnCoreNLP(annotations);

	            Annotation annotation = new Annotation(userQueryString);
	            pipeline.annotate(annotation);
	            Sentence firstSen = annotation.getSentences().get(0);

	            // Create a BooleanQuery for AND operation
	            BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();

	            // Add each term as a separate clause with MUST (AND) operator
	            String[] terms = firstSen.getWordSegmentedSentence().toString().split("\\s+");
	            for (String term : terms) {
	                TermQuery termQuery = new TermQuery(new Term("heading", term));
	                if(term.contains("_")) {
	                	booleanQuery.add(termQuery, Occur.MUST);
	                }
	                else {
	                	booleanQuery.add(termQuery, Occur.SHOULD);
	                }
	            }

	            TopDocs topDocs = searcher.search(booleanQuery.build(), 100);
	            
	            int totalHits = topDocs.scoreDocs.length;
	            // Process search results
	            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
	                int docId = scoreDoc.doc;
	                
	                Document document = searcher.doc(docId);

	                SearchResult result = new SearchResult();
	                result.setVanbanId(document.get("VanbanId"));
	                result.setHeading(document.get("heading").replace("_", " ").replaceAll("\\s*,\\s*", ", "));
	                result.setHtml(document.get("html"));
	                result.setPageSize(totalHits);
	                searchResults.add(result);
	            }

	            reader.close();

	            if (searchResults.isEmpty()) {
	                return null;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    List<List<SearchResult>> parts = chopped(searchResults, 10);
	    List<SearchResult> rs = parts.get(pageNumber);
	    return rs;
	}
    
    public void textExtract() throws IOException {
    	String[] annotations = {"wseg"};
    	VnCoreNLP pipeline = new VnCoreNLP(annotations);
    	
    	String str = "Quyết định 38/2023/QĐ-UBND quy định về giá sản phẩm, dịch vụ công ích thủy lợi trên địa bàn tỉnh Vĩnh Long năm 2023";
    	Annotation annotation = new Annotation(str);
    	pipeline.annotate(annotation);
    	
    	System.out.println(annotation.toString());
    	
    	PrintStream outputPrinter = new PrintStream("E:\\output.txt");
    	pipeline.printToFile(annotation, outputPrinter);
    	
    	Sentence firstSen = annotation.getSentences().get(0);
    	firstSen.getWordSegmentedSentence().toString();
    	System.out.println(firstSen.getWordSegmentedSentence().toString());
    }
}
