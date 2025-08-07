# Author: Kristie O'Brien
# Date: 7/19/2025
# Class: CS 499 Senior Capstone
# Project: Artifact One Text Based Game

# Description of Project:
# The following code was from a final project in my IT 140 class near the start of my computer science degree.
# Changes have been made to substantially increase the complexity of the game, including but not limited to,
# game difficulty levels, crafting items, hidden items, a point system to determine game win or lose,
# and a secret ending depending on points collected.

# Import necessary libraries for handling loading an image
from PIL import Image

# A dictionary for the text based Space Game
# The dictionary links a room to other rooms.
# It also connects the items in that room to the specific room in which the item is found.
# Note that there are 8 rooms total with various items in all rooms except the Recreation Room.
# The Research Lab contains the Evil Robot which is the final boss of the game. Entering this room ends the game.
rooms = {
        'Recreation Room': {'South': 'Med Bay', 'North': 'Galley', 'West': 'Supplies Room', 'East': 'Clean Room'},
        'Galley': {'South': 'Recreation Room', 'East': 'Bedroom', 'item': 'F.L.A.V.O.R.S.', 'item2': 'B.I.T.T.E.R.', 'craftingItem': 'Compass-Face'},
        'Bedroom': {'West': 'Galley', 'item': 'Cypher', 'item2': 'Corrupted-Cypher', 'hiddenItem': 'Shield-of-Justice'},
        'Supplies Room': {'East': 'Recreation Room', 'item': 'L.A.M.E.', 'hiddenItem': 'Key-of-Knowledge', 'craftingItem': 'Magnet'},
        'Med Bay': {'North': 'Recreation Room', 'East': 'Mainframe Room', 'item': 'O.U.C.H.', 'item2': 'Expired-O.U.C.H.'},
        'Mainframe Room': {'West': 'Med Bay', 'item': 'Q.U.A.I.L.', 'craftingItem': 'Geode'},
        'Clean Room': {'West': 'Recreation Room', 'North': 'Research Lab', 'item': 'Environmental-Suit', 'hiddenItem': 'Sword-of-Truth'},
        'Research Lab': {'South': 'Clean Room', 'item': 'Evil Robot'}
    }


# This defines the function for the story. The story gives the user background and context for the game
# All of the following print lines contain the story that will be displayed to the user
def story():
    print('Oh no! An evil robot is loose in the research lab! Aboard your starship, the Intrepid Archivist, currently located in deep space, you have been')
    print('conducting sensitive research on technology based on Archaeological discoveries. The research you are conducting has the potential to forever reshape')
    print('galactic knowledge of science. It is no wonder your arch enemy, Dr. Nemesis, would have smuggled on board a traitorous robot to steal or sabotage your')
    print('vital research. Now this robot has taken up residence in your lab. But it does not know yet that you know it is there. So, there is still time to save')
    print('your work! All is not lost, but you will need to prepare yourself to vanquish the evil robot. It is your lab after all, so you know the lay')
    print('of the land, giving you a distinct advantage. The first thing you will need is the Cypher, so you can have access to emergency')
    print('security systems aboard the Intrepid Archivist. Next, you will need your trusty Quantum Unlimited Apparatus Initialized Life-system, or Q.U.A.I.L. for')
    print('short. Q.U.A.I.L. is your ship’s on-board computer. Its life simulation suit’s shared name and appearance with')
    print('the common earth bird is pure coincidence I’m sure. Q.U.A.I.L. will allow you to unlock the lab and look up schematics for the robot. The third thing')
    print('you will want to grab is a hardy Free Range Legume Amino acid Variable Organic Resources Snack or F.L.A.V.O.R.S for short. This will')
    print('give you the energy you need to fight off the robot. Despite the name, many in the galactic community rumor that it is tasteless, though high in')
    print('nutrition. The fourth thing you will need is a Laser Armed Mass Emitter, or L.A.M.E for short. This will allow you')
    print('to shoot lasers at the robot when you fight. Don’t worry, I’m sure it is way cooler than its acronym makes it sound. The fifth thing you want is a')
    print('standard issue Over the counter Ubiquitous Clinical Help, also known as O.U.C.H for short, in case of unforeseen injury.')
    print('Lastly, you will need your protective Environmental-Suit.')
    print('Also keep in mind, there may be hidden items around your ship that can help you out! Maybe Q.U.A.I.L. knows something about them...')

# This sets up the function for player_status that will display the players current status as they continue.
# Player status, in the context of this game, is room location and inventory items.
def player_status():
    print('you are currently in the {}'.format(current_room))
    print('inventory:', inventory)

    # The following IF blocks cover logic to handle special cases for player status.
    # These special cases happen when a player enters a room and see an item not in their inventory.
    # Certain items are hidden unless the user has Q.U.A.I.L. with them.
    if ('item' in rooms[current_room]) and (rooms[current_room]['item'] not in inventory):
        print('You see:', rooms[current_room]['item'] )

    if ('item2' in rooms[current_room]) and (rooms[current_room]['item2'] not in inventory):
        print('You see:', rooms[current_room]['item2'])

    if ('hiddenItem' in rooms[current_room]) and (rooms[current_room]['hiddenItem'] not in inventory) and ('Q.U.A.I.L.' in inventory):
        print('Now that you have Q.U.A.I.L. with you, she shows you where you can find:', rooms[current_room]['hiddenItem'])

    if ('craftingItem' in rooms[current_room]) and (rooms[current_room]['craftingItem'] not in inventory) and ('Q.U.A.I.L.' in inventory) and ('Compass-of-the-Dawn' not in inventory):
        print('Now that you have Q.U.A.I.L. with you, she shows you where you can find a crafting item called:', rooms[current_room]['craftingItem'])

# The following code details the logic for the player to select their difficulty level.
# If the player selects hard, they cannot see the map or start off with Q.U.A.I.L. in their inventory.
# If the player selects easy, they can see the map and start off with Q.U.A.I.L. in their inventory.
def difficulty_level_selection():
    user_difficulty_choice= input("Please select your difficulty level: (easy/hard): ")
    if user_difficulty_choice == 'easy':
        inventory.append('Q.U.A.I.L.')
        try:
            img = Image.open("Intrepid Archivist Map.png")
            img.show()
        except FileNotFoundError:
            print("An error has occurred: the image file was not found.")

# The following code prints the came instructions to the user when called.
def game_instructions():
    print('Welcome aboard the Intrepid Archivist! To move around, please enter commands such as go North, go South, go East, go West. These will allow you to move about the map')
    print('Please note, game commands must be typed as they appear in the instructions.')
    print('So go should begin with a lower case letter and compass directions must begin with an upper case letter')
    print('To vanquish the robot you will also need to collect six items on board the ship')
    print('If an item is available to you in a room, its name will appear on the display when your room location is displayed')
    print('You can add the item in the room to your inventory by typing as your command get [item name]')
    print('Please note, you must type get to begin with a lower case letter and the item name exactly as it appears on the display')
    print('When you have gathered all six items, find the Research Lab to face the Evil Robot!')

# The following code prints the secret ending message to the user when called.
def secret_ending():
    print('With the help of Q.U.A.L., you were able to track down some items amongst your ship you did not know just how valuable they were before.')
    print('By using them to defeat the evil robot, you notice that the items with you take on a luminous glow.')
    print('Q.U.A.I.L. regards you with a knowing look, and speaks "You passed the test, intrepid adventurer! By using these ancient technologies to defeat the evil robot, ')
    print('you have proven yourself to be the next protector of the starry cosmos, fighting evil wherever it may come from. The secrets of the Intrepid Archivist are yours now, now let us go find a new adventrue!')
    print('You take up your new title and head to the stars, ready to take up the call to protect the universe with Q.U.A.I.L. at your side')

# The following code handles the logic for the end game when called.
# The purpose of this logic is to calculate the points the user has based on assigned points.
# Points are assigned in a dictionary called item_points.
# Depending on how many points the user has depends on what print statement or statements they get in the end game.
def end_game():
    item_points = {'F.L.A.V.O.R.S': 1,
                   'B.I.T.T.E.R.': -1,
                   'Cypher': 1,
                   'Corrupted-Cypher': -2,
                   'L.A.M.E.': 1,
                   'O.U.C.H': 1,
                   'Expired-O.U.C.H': -1,
                   'Q.U.A.I.L.': 1,
                   'Sword-of-Truth': 10,
                   'Shield-of-Justice': 10,
                   'Key-of-Knowledge': 10,
                   'Environmental-Suit': 1,
                   'Compass-Face': 0,
                   'Geode': 0,
                   'Magnet': 0,
                   'Compass-of-the-Dawn': 30}

    total_points = 0

    for item in inventory:
        item_value = item_points.get(item, 0)
        total_points += item_value

    if total_points < 6:
        print('Oh no, the research is lost and the robot won! Game Over. Thank you for playing!')

    elif total_points >= 6:
        print('Congratulations!!! You vanquished the Evil Robot and saved your research!!! Thank you for playing!')
        if total_points >= 30:
            print('Congratulations!!! You unlocked the secret ending!!!')
            secret_ending()

# The following code initializes the variable current_room and sets its starting value as the Recreation Room.
current_room = 'Recreation Room'

# The following code sets up an empty inventory that the user can append items to throughout the game.
inventory = []

# The following code calls the story to display.
story()

# The following code prints a simple line of dashes that break up the text.
# Doing this aids in the readability of the text when it is all printed.
print('-----------------------------------------------------------------')

# The following code prompts the user for input to start the next part of the game.
# This allows for a break in text so that all the text does not appear at once.
# Note, there is no invalid input here, the user can enter anything they want, so long as they hit enter at
# the end, the game will still start.
input('After reading the story and when you are ready, click in front of this sentence and press the enter key to continue:')

# The following code calls the difficulty_level_selection function.
difficulty_level_selection()

# The following code prints another line of dashes to help with the readability of the code
print('-----------------------------------------------------------------')

# The following code calls the game instructions function to print
game_instructions()

# The following code prints another line of dashes to help with the readability of the code
print('-----------------------------------------------------------------')

# The following code begins the while loop that will contain the running of the game. It will continue until the game is either won or lost.
# The contents of this loop handles the logic for the player moving about the map and collecting items.
# Furthermore, this code uses additional logic to handle what happens if the user enters the Research Lab.
# Some functions are used and some code logic is handled within the loop itself.
# Ways to break or continue the loop are placed throughout to prevent a never ending loop.
while True:
    # The following code calls the player_status function and will do so at the start of every new turn.
    player_status()
    print('-----------------------------------------------------------------')

    # The following code checks to see if the user has entered the end game by entering the Research Lab.
    # Note, entering that room breaks the loop.
    if current_room == 'Research Lab':
        end_game()
        break

    # The following code takes in user input and splits it in two for ease of error handling..
    user_input = input('Please enter a command:')
    user_split_input = user_input.split()

    # The following IF and ELIF blocks handle the logic for if a user entered incorrect input or input that is correct,
    # whether they are getting an item or going to a new room location.
    if user_input == '':
        print('Your command should contain content such as go [compass direction] or get [item name].')

    # The following ELIF block specifically handles a user trying to go to a new room location.
    elif user_split_input[0] == 'go':

        if user_split_input[1] in rooms[current_room]:
            current_room = rooms[current_room][user_split_input[1]]

        else:
            print('I am sorry, that is not a valid direction and/or you cannot go that way.')

    # This ELIF block specifically handles logic related to a user trying to get an item.
    # Note that this code handles all item types such as standard items, hidden items,
    # and crafting items.
    elif user_split_input[0] == 'get':

        room = rooms[current_room]

        if current_room == 'Recreation Room':
            print('I am sorry, but there is not an item in this room. Please try another. Use the go command to move between rooms.')
            continue

        if room.get('item') == user_split_input[1]:

            if user_split_input[1] not in inventory:
                inventory.append(user_split_input[1])
                print(user_split_input[1], 'has been retrieved! You can now see it in your inventory.')

            else:
                print('That item is already in your inventory!')
                continue
        elif room.get('item2') == user_split_input[1]:

            if user_split_input[1] not in inventory:
                inventory.append(user_split_input[1])
                print(user_split_input[1], 'has been retrieved! You can now see it in your inventory.')

            else:
                print('That item is already in your inventory!')
                continue

        elif room.get('hiddenItem') == user_split_input[1]:

            if 'Q.U.A.I.L.' not in inventory:
                print('Hmmm...something for sure seems to be here but you can not quite see it yet. Maybe you need some extra help? Check the ship and come back later!')

            if 'Q.U.A.I.L.' in inventory:

                if user_split_input[1] not in inventory:
                    inventory.append(user_split_input[1])
                    print(user_split_input[1], 'has been retrieved! You can now see it in your inventory.')

                else:
                    print('That item is already in your inventory!')
                    continue

        elif room.get('craftingItem') == user_split_input[1]:

            if 'Q.U.A.I.L.' not in inventory:
                print('Hmmm...something for sure seems to be here but you can not quite see it yet. Maybe you need some extra help? Check the ship and come back later!')

            if 'Q.U.A.I.L.' in inventory:

                if user_split_input[1] not in inventory:
                    inventory.append(user_split_input[1])
                    print(user_split_input[1], 'has been retrieved! You can now see it in your inventory.')

                    if 'Magnet' in inventory and 'Geode' in inventory and 'Compass-Face' in inventory:
                        inventory.remove('Magnet')
                        inventory.remove('Geode')
                        inventory.remove('Compass-Face')
                        inventory.append('Compass-of-the-Dawn')
                        print('Congratulations! You have collected all three of the crafting items and now have a super special item added to your inventory!')

                else:
                    print('That item is already in your inventory!')
                    continue
        else:
            print('I am sorry, the item is not in that room or you have entered an invalid item.')
            continue

    # This else statement acts as a catch all for any other incorrect input.
    else:
        print('I am sorry, that is not a valid command. Please try again.')
