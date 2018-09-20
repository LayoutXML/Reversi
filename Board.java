public class Board {

	private Arraylist<Chip> chipList = new ArrayList<>();

	public void createBoard() {
		for (int i=0; i<64; i++) {
			Chip chip = new Chip;
			chipList.add(chip);
		}
		chipList[27].changeSide(true);
		chipList[28].changeSide(false);
		chipList[35].changeSide(false);
		chipList[36].changeSide(true);
		Game.playerTurn = 1;
	}

	public void placeChip(Integer index) {
		if (checkIfChipCanBePlaced(index, playerTurn)) {
			if (Game.playerTurn==1)
				side = false;
			else
				side = true;
			chipList[index].changeSide(side);
			// ... change inbetween chips
			if (Game.playerTurn==1)
				Game.playerTurn = 2;
			else
				Game.playerTurn = 1;
		}
		
	}

}