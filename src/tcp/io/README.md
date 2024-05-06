# Java IO 입출력 스트림

***

## 1. Overview

Java는 `Input Stream`과 `Output Stream`을 통해 데이터를 입/출력한다. Stream은 단방향으로 데이터가 흐르는 것을 의미하며 `Byte Stream`과 `Text Stream`으로 분류된다.

<img src="/images/io/stream_standalone.png" width="100%" alt="stream_standalone.png">
<img src="/images/io/stream.png" width="100%" alt="stream.png">

## 2. Byte Stream과 Text Stream

자바 I/O는 ByteStream으로 `Input/OutputStream`이라는 상위 추상클래스를 제공하고 TextStream으로 `Reader/Writer`라는 상위 추상클래스를 제공한다.

<img src="/images/io/byte_text_stream.png" width="100%" alt="byte_text_stream.png">

## 3. OutputStream - 출력스트림

OutputStream Example
```java
// Byte OutputStream Example
OutputStream os = new FileOutputStream("./src/byte.txt");  
  
byte[] bytes = {10, 20, 30, 40, 50, 60, 70};  
os.write(bytes);  
  
os.flush(); /* (1) */
os.close();  
  
// Text OutputStream Example
Writer writer = new FileWriter("./src/text.txt");  
writer.write("Hello, World!");  
  
char[] chars = {'a', 'b', 'c', 'd', 'e'};  
writer.write(chars);  
  
writer.flush();  
writer.close();
```

(1):  OutputStream은 내부 버퍼를 가지고 있는데 내부 버퍼에 남아있는 바이트를 출력하고 버퍼를 비우는 역할을 한다. 문자도 마찬가지로 문자를 비우고 버퍼를 비운다.


## 4. InputStream - 입력스트림

InputStream Example
```java
// Byte InputStream Example  
InputStream is = new FileInputStream("./src/byte.txt");  
  
byte[] readBytes = new byte[128];  
while(is.read(readBytes) != -1) {  
    for (byte readByte : readBytes) {  
        System.out.print(readByte + " ");  
    }  
}  
  
is.close();
  
// Text InputStream Example  
Reader reader = new FileReader("./src/text.txt");  
char[] readChars = new char[128];  
while(reader.read(readChars) != -1) {  
    for (char readChar : readChars) {  
        System.out.print(readChar + " ");  
    }  
}  
  
reader.close();
```


## 5. SubStream - 보조스트림

보조스트림은 다른 스트림과 연결되어 동작하고 자체 입출력은 수행할 수 없다. ByteStream을 TextStream으로 변환하고 성능향상을 위해 BufferedStream을 보조스트림을 추가하는 등으로  활용할 수 있다.

<img src="/images/io/substream.png" width="100%" alt="substream.png">

```java
// OutputStream을 Writer로 변환  
OutputStream os = new FileOutputStream("./src/byte.txt");  
Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);  
  
writer.write("Hello World");  
  
writer.flush();  
writer.close();  
  
// InputStream을 Reader로 변환  
InputStream is = new FileInputStream("./src/byte.txt");  
Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);  
  
char[] readChars = new char[1024];  
while(reader.read(readChars) != -1) {  
    for (char readChar : readChars) {  
        System.out.print(readChar + " ");  
    }  
}  
  
reader.close();
```

## 6. BufferedStream - 성능 향상 스트림

자바는 BufferedStream(성능 향상 스트림)을 제공한다. 입출력 소스를 디스크와 직접 작업하지 않고 중간에 위치한 메인 메모리 버퍼와 작업하면서 I/O 실행 성능을 향상시킨다.

<img src="/images/io/buffered_stream.png" width="100%" alt="buffered_stream.png">

```java
import java.tcp.io.*;  
  
public class Main {  
    public static void main(String[] args) throws IOException {  
		
		//BufferedWriter, BufferedReader 동일
        long streamTime = stream();  
        System.out.println("\nstreamTime = " + streamTime);  
  
        long bufferedStreamTime = bufferedStream();  
        System.out.println("\nbufferedStreamTime = " + bufferedStreamTime);  
  
    }  
  
    public static long stream() throws IOException {  
  
        long start = System.currentTimeMillis();  
  
        OutputStream os = new FileOutputStream("./src/byte.txt");  
  
        byte[] bytes = {10, 20, 30, 40, 50};  
        os.write(bytes);  
  
        os.flush();  
        os.close();  
  
        InputStream is = new FileInputStream("./src/byte.txt");  
  
        byte[] readBytes = new byte[1024];  
        while(is.read(readBytes) != -1) {  
            for (byte readByte : readBytes) {  
                System.out.print(readByte + " ");  
            }  
        }  
  
        is.close();  
  
        return System.currentTimeMillis()-start;  
    }  
  
    public static long bufferedStream() throws IOException {  
        long start = System.currentTimeMillis();  
  
        OutputStream os = new FileOutputStream("./src/byte.txt");  
        BufferedOutputStream bos = new BufferedOutputStream(os);  
  
        byte[] bytes = {10, 20, 30, 40, 50};  
        bos.write(bytes);  
  
        bos.flush();  
        bos.close();  
  
        InputStream is = new FileInputStream("./src/byte.txt");  
        BufferedInputStream bis = new BufferedInputStream(is);  
  
        byte[] readBytes = new byte[1024];  
        while(bis.read(readBytes) != -1) {  
            for (byte readByte : readBytes) {  
                System.out.print(readByte + " ");  
            }  
        }  
  
        bis.close();  
  
        return System.currentTimeMillis() - start;  
    }  
}
```

<img src="/images/io/buffered_stream_time.png" width="100%" alt="buffered_stream_time.png">

## 7. ObjectStream - 객체 스트림

ObjectStream을 이용하면 객체를 파일 또는 네트워크로 입/출력할 수 있다. 이 과정에서 객체를 바이트로 변경하는 `직렬화(Serialization)`와 바이트를 객체로 변경하는 `역직렬화(Deserialization)`가 사용된다.

```java
import java.tcp.io.*;  
  
public class Main {  
    public static void main(String[] args) throws IOException, ClassNotFoundException {  
        OutputStream os = new FileOutputStream("./src/byte.txt");  
        ObjectOutputStream oos = new ObjectOutputStream(os);  
  
        // Serialization해서 byte로 저장  
        Member outputMember = new Member("name", 26);  
        oos.writeObject(outputMember);  
  
        oos.flush();  
        oos.close();  
  
        InputStream is = new FileInputStream("./src/byte.txt");  
        ObjectInputStream ois = new ObjectInputStream(is);  
  
        // byte로 저장된 Member 객체를 Deserialization
        Member inputMember = (Member) ois.readObject();  
  
        System.out.println(outputMember);  
        System.out.println(inputMember);  
    }  
}  

// Java는 Serializable implements하는 클래스만 직렬화 할 수 있도록 제한
class Member implements Serializable {  
    private final String name;  
    private final int age;  
  
    public Member(String name, int age) {  
        this.name = name;  
        this.age = age;  
    }  
  
    @Override  
    public String toString() {  
        return "name: " + name + ", age: " + age;  
    }  
}
```

외에도 기본 타입 스트림, 프린트 스트림이 있지만, 크게 중요하지 않은 내용이라고 생각해 쿨하게 생략
