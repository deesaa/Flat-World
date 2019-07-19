package FlatWorld;

public class DestructMaterialRatio{
	int materialId, power;
	
	public DestructMaterialRatio(MaterialClass wood, int power) {
		this.materialId = wood.materialId;
		this.power      = power;
	}
}