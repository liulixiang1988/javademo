package io.liulixiang.hadoop.hdfs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Hadoop HDFS Java API Operation
 *
 * @since 2021-05-16
 */
public class HDFSApp {

  public static final String HDFS_PATH = "hdfs://localhost:8020";
  FileSystem fileSystem = null;
  Configuration configuration = null;

  @Before
  public void setUp() throws Exception {
    configuration = new Configuration();
    fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, "liulixiang");
    System.out.println("HDFS startup");
  }

  @After
  public void tearDown() throws Exception {
    configuration = null;
    fileSystem = null;
    System.out.println("HDFS teardown");
  }

  @Test
  public void mkdir() throws Exception {
    fileSystem.mkdirs(new Path("/hdfsapi/test"));
  }

  @Test
  public void create() throws Exception {
    // 注意，这里没有用core-site.xml的配置，默认是三副本
    FSDataOutputStream output = fileSystem.create(new Path("/hdfsapi/test/a.txt"));
    output.write("hello hadoop".getBytes());
    output.flush();
    output.close();
  }

  @Test
  public void cat() throws Exception {
    FSDataInputStream input = fileSystem.open(new Path("/hdfsapi/test/b.txt"));
    IOUtils.copyBytes(input, System.out, 1024);
    input.close();
  }

  @Test
  public void rename() throws Exception {
    Path oldPath = new Path("/hdfsapi/test/a.txt");
    Path newPath = new Path("/hdfsapi/test/b.txt");
    fileSystem.rename(oldPath, newPath);
  }

  @Test
  public void copyFromLocalFile() throws Exception {
    Path localPath = new Path("E:/test.txt");
    Path hdfsPath = new Path("/hdfsapi/test/");
    fileSystem.copyFromLocalFile(localPath, hdfsPath);
  }

  @Test
  public void copyFromLocalFileWithProgressBar() throws Exception {
    try (InputStream fileIns = new FileInputStream(new File("F:\\Software\\hadoop-2.10.1.tar.gz"));
        InputStream in = new BufferedInputStream(fileIns)) {
      FSDataOutputStream output = fileSystem.create(new Path("/hdfsapi/test/hdf-2.10.1.tar.gz"),
          () -> System.out.print("."));
      IOUtils.copyBytes(in, output, 4096);
      output.close();
    }
  }

  @Test
  public void copyToLocalFile() throws Exception {
    Path localPath = new Path("E:/b.txt");
    Path hdfsPath = new Path("/hdfsapi/test/b.txt");
    fileSystem.copyToLocalFile(hdfsPath, localPath);
  }

  @Test
  public void listFiles() throws Exception {
    // 注意，可以看到用hadoop cli创建的文件副本数是1，使用java API创建的文件是3副本
    FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/hdfsapi/test"));
    for (FileStatus fileStatus : fileStatuses) {
      String isDir = fileStatus.isDirectory() ? "folder" : "file";
      short replication = fileStatus.getReplication();
      long len = fileStatus.getLen();
      String path = fileStatus.getPath().toString();
      System.out.println(isDir + "\t" + replication + "\t" + len + "\t" + path);
    }
  }

  @Test
  public void delete() throws Exception {
    Path hdfsPath = new Path("/hdfsapi/test/");
    boolean result = fileSystem.delete(hdfsPath, true);
    System.out.println("delete result: " + result);
  }
}
