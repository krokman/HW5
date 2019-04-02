public class TestMain {
	public static void main(String[] args) {
		MyMap<String, Integer> myHashMap = new MyHashMap<>();
		myHashMap.put("Alex", 15);
		myHashMap.put("Nick", 20);
		myHashMap.put("Stan", 30);
		myHashMap.put("Fort", 25);
		myHashMap.put("Nick", 85);

		System.out.println(myHashMap.get("Stan"));
		System.out.println(myHashMap.get("Alex"));
		System.out.println(myHashMap.get("Stan"));
		System.out.println("Deleted Stan - " + myHashMap.remove("Stan"));
		System.out.println(myHashMap.get("Fort"));
		System.out.println(myHashMap.get("Nick"));

		System.out.println(myHashMap.toString());
		System.out.println("Size = " + myHashMap.size());

		myHashMap.clear();
		myHashMap.put("Fort", 25);
		myHashMap.put("Fort1", 35);
		System.out.println(myHashMap.toString());
	}
}
