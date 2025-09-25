package testmo

import groovy.json.JsonOutput
import java.net.HttpURLConnection
import java.net.URL

class Reporter {
	static String TESTMO_URL = "https://sigtuple.testmo.net/api/v1"
	static String API_TOKEN = "testmo_api_eyJpdiI6IjNsWTQrNVg2aXBmdmgwYXZQaGN1Q1E9PSIsInZhbHVlIjoiRWFRTUoyUXpsRkpLZEtCdWwrODE0cVpuSDkwY2VTM0dZaHJVWFIzM09zQT0iLCJtYWMiOiJlNzA4YzY1YzU3ZTQzNGM1ODM5ODlmMmUyOWM5N2RjM2M4Yzk0YzNmOWE2MmExMWQyZTBiMTIzMGNkZTIyMDU5IiwidGFnIjoiIn0="
	static String RUN_ID = "29"   // Active Test Run ID in Testmo

	static void updateResult(String caseId, String status, String comment = "") {
		def url = new URL("${TESTMO_URL}/runs/${RUN_ID}/results")
		def connection = (HttpURLConnection) url.openConnection()
		connection.setRequestMethod("POST")
		connection.setRequestProperty("Content-Type", "application/json")
		connection.setRequestProperty("Authorization", "Bearer ${API_TOKEN}")
		connection.doOutput = true

		def body = [
			results: [
				[
					case_id: caseId.replace("C", "").toInteger(),
					status: status,
					comment: comment
				]
			]
		]

		def writer = new OutputStreamWriter(connection.outputStream)
		writer.write(JsonOutput.toJson(body))
		writer.flush()
		writer.close()

		connection.inputStream.text  // read response
	}
}
