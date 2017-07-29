package eventplannerUT;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class SchemaOutputResolver extends javax.xml.bind.SchemaOutputResolver {

	@Override
	public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
		File file = new File(suggestedFileName);
		StreamResult result = new StreamResult(file);
		result.setSystemId(file.toURI().toURL().toString());
		return result;
	}
	
	public SchemaOutputResolver(String namespaceURI, String suggestedFileName) {
		try {
			createOutput(namespaceURI, suggestedFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SchemaOutputResolver() {
		
	}

}
