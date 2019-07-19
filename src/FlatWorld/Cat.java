package FlatWorld;

class Cat{
	  String name;
	  int Damage, Speed;
	  Cat(String n) { name = n; }
	  public void talk() { System.out.println("Cat " + name + " meows!"); 
	  					   System.out.println("Speed = " + Speed);
	  					   System.out.println("Damage = " + Damage);}
	  public void walk() { System.out.println("Cat " + name + " walks..."); }
	  
	  public void setDamage(int Damage){
		  this.Damage = Damage;
	  }
	  public void setSpeed(int Speed){
		  this.Speed = Speed;
	  }
}