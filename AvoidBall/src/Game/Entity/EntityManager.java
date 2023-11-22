package Game.Entity;

public class EntityManager {
	//상수로 바꿀
	private int MAX_ENTITY = 255;
	private Entity entity_list[] = new Entity[MAX_ENTITY];
	private int last_entity_id = 0;
	private int temp;
	public EntityManager() {
		temp = 1;
	}
	
	public Boolean Register(Entity entity) {
		if(last_entity_id <= MAX_ENTITY) {
			System.out.println("[ERROR] Failed to register entity to EntityManager, So many entity.");
			return false;
		}
		entity_list[last_entity_id++] = entity;
		return true;
	}
}
