package de.fokus.fraunhofer.hopswork.action;

public class CreateDataSetAction implements IHopsworkAction {


    //post request

    //http://bbc6.sics.se:8080/hopsworks-api/api/project/1/dataset/createTopLevelDataSet

    /*
    params
    description	test
generateReadme	true
name	test123
searchable	true
     */

    public CreateDataSetAction(String dataSetName, String dataSetDescription, boolean searchable,boolean generateReadMe){

    }

    @Override
    public void execute() throws Exception {

        //TODO
     /*   CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.example.com");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", "John"));
        params.add(new BasicNameValuePair("password", "pass"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
        */
    }
}
