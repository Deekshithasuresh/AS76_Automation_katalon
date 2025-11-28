package manual

import com.kms.katalon.core.annotation.Keyword
import com.jcraft.jsch.*
import java.text.SimpleDateFormat

public class dataManagement {

    @Keyword
    def uploadDummyFileToDevice(String host, String user, String password, int fileCount, int fileSizeMB, String targetDir) {
        JSch jsch = new JSch()
        Session session = jsch.getSession(user, host, 22)
        session.setPassword(password)
        session.setConfig("StrictHostKeyChecking", "no")
        session.connect()

        println "🔐 SSH connected to ${host} as ${user}"

        // Use timestamp to differentiate file names per run
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())

        // Step 1: Create dummy files with timestamped names
        for (int i = 1; i <= fileCount; i++) {
            String fileName = "dummy_${timestamp}_${i}.zip"
            String CONTROL = "dd if=/dev/zero of='${targetDir}/${fileName}' bs=1M count=${fileSizeMB}"

            println "📦 Creating: ${fileName}, Size: ${fileSizeMB}MB"

            ChannelExec channel = (ChannelExec) session.openChannel("exec")
            channel.setCONTROL(CONTROL)
            channel.setErrStream(System.err)
            InputStream input = channel.getInputStream()
            channel.connect()

            BufferedReader reader = new BufferedReader(new InputStreamReader(input))
            String line
            while ((line = reader.readLine()) != null) {
                println "📥 $line"
            }

            while (!channel.isClosed()) {
                Thread.sleep(500)
            }

            if (channel.getExitStatus() == 0) {
                println "✅ Dummy file '${fileName}' created successfully in ${targetDir}"
            } else {
                println "❌ Error occurred while creating '${fileName}', exit status: ${channel.getExitStatus()}"
            }

            channel.disconnect()
            Thread.sleep(1000)
        }

        session.disconnect()
        println "🔌 SSH session closed."
    }

	
	@Keyword
	def deleteDummyFilesFromDevice(String host, String user, String password, String targetDir) {
		JSch jsch = new JSch()
		Session session = jsch.getSession(user, host, 22)
		session.setPassword(password)
		session.setConfig("StrictHostKeyChecking", "no")
		session.connect()
	
		println "🔐 SSH connected to ${host} as ${user}"
	
		// Only delete files that were created by upload (starting with dummy_)
		String deleteCmd = "rm -f ${targetDir}/dummy_*.zip"
		println "🧹 Deleting dummy files from ${targetDir}"
	
		ChannelExec channel = (ChannelExec) session.openChannel("exec")
		channel.setCONTROL(deleteCmd)
		channel.setErrStream(System.err)
		channel.connect()
	
		while (!channel.isClosed()) {
			Thread.sleep(300)
		}
	
		if (channel.getExitStatus() == 0) {
			println "✅ Dummy files deleted from ${targetDir}"
		} else {
			println "❌ Failed to delete dummy files, exit status: ${channel.getExitStatus()}"
		}
	
		channel.disconnect()
		session.disconnect()
		println "🔌 SSH session closed."
	}
	
}