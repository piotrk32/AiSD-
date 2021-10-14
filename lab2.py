class House:
    doors: int
    color: str

    def __init__ (self, doors: int, color: str)-> None:
        self.doors = doors
        self.color = color

    def change_color(self, new_color: str)->None:
        if new_color == self.color:
            raise ValueError("Color values error")
            self.color = new_color

    def __len__(self) -> int:
        return 8

    def __str__(self) -> str:
        return f'liczba drzwi: {self.doors} ' \
            f'kolor elewacji {self.color}


        self.color = new_color

green_house: House = House(doors = 15, color = "green")
print(green_house.doors)
print(green_house)