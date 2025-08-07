# My name is Kristie O'Brien
# Class: IT 140
# Project: Text Based Game

# A dictionary for the text based Space Game
# The dictionary links a room to other rooms.

rooms = {  # This sets up the dictionary for the game showing the rooms, which rooms link to each other, and the item in the room if there is one
        'Recreation Room': {'South': 'Med Bay', 'North': 'Galley', 'West': 'Supplies Room', 'East': 'Clean Room'},  # This shows the information for the
                                                                                                                    # Recreation Room
        'Galley': {'South': 'Recreation Room', 'East': 'Bedroom', 'item': 'F.L.A.V.O.R.S.'},  # This shows the information for the Galley
        'Bedroom': {'West': 'Galley', 'item': 'Cypher'},  # This shows the information for the Bedroom
        'Supplies Room': {'East': 'Recreation Room', 'item': 'L.A.M.E.'},  # This shows the information for the Supplies room
        'Med Bay': {'North': 'Recreation Room', 'East': 'Mainframe Room', 'item': 'O.U.C.H.'},  # This shows the information for the Med Bay
        'Mainframe Room': {'West': 'Med Bay', 'item': 'Q.U.A.I.L.'},  # This shows the information for the Mainframe Room
        'Clean Room': {'West': 'Recreation Room', 'North': 'Research Lab', 'item': 'Environmental-Suit'},  # This shows the information for the Clean Room
        'Research Lab': {'South': 'Clean Room', 'item': 'Evil Robot'}  # This shows the information for the Research Lab
    }  # Note that the Recreation Room does not contain an item and the Research Lab contains the villain


def story():  # This defines the function for the story. The story gives the user background and context for the game
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
# All of the above print lines contain the story that will be displayed to the user

def player_status():  # This sets up the function for player_status that will display the players current status as they continue
    print('you are currently in the {}'.format(current_room))  # Since we are only dealing with room location, that is the only status that will be maintained
                                                               # If we were dealing with inventory, that could be displayed with the player status
                                                               # The above print statement is for printing out the current room name
                                                               # This will let the player know where they are throughout the game
    print('inventory:', inventory) # This tells the inventory to print when the player status is called

    if ('item' in rooms[current_room]) and (rooms[current_room]['item'] not in inventory):  # This asks if the item is in the current room and if the item is not
                                                                                            # already in the inventory
        print('You see:', rooms[current_room]['item'])  # If the above criteria are true, this message is printed displaying the item name
                                                        # By ordering it with the assigned logic, it makes sure the name of an item will not
                                                        # be repeated
                                                        # after it is added to an inventory.
                                                        # So if a player returns to a room, the item name will not be displayed again


def game_instructions():  # This sets up the function for game instructions. It will display at the start of the game though not within the while loop
    print('Welcome aboard the Intrepid Archivist! To move around, please enter commands such as go North, go South, go East, go West. These will allow you to '
          'move about the map')  # This welcomes the player and explains the command instructions for moving about the map

    print('Please note, game commands must be typed as they appear in the instructions.')  # This explains that the commands must be typed as they appear
                                                                                           # here to be valid
    print('So go should begin with a lower case letter and compass directions must begin with an upper case letter')  # This continues the explanation about
                                                                                                                      # instructions
    print('To vanquish the robot you will also need to collect six items on board the ship') # This tells the user they need six items to win the game
    print('If an item is available to you in a room, its name will appear on the display when your room location is displayed')  # This explains how items are found
    print('You can add the item in the room to your inventory by typing as your command get [item name]')  # This explains how to get the items
    print('Please note, you must type get to begin with a lower case letter and the item name exactly as it appears on the display')  # This explains how to type
                                                                                                                                      # the input so that it
                                                                                                                                      # is valid
    print('When you have gathered all six items, find the Research Lab to face the Evil Robot!')  # This explains how to end the game


current_room = 'Recreation Room'  # This initializes the variable current_room and sets its starting value as the Recreation Room

inventory = []  # This sets up an empty inventory that the user can append items to throughout the game


story()  # This calls the story to display
print('-----------------------------------------------------------------')  # This prints a simple line of dashes that break up the text
                                                                            # Doing this aids in the readability of the text when it is all printed
input('After reading the story and when you are ready, click in front of this sentence and press the enter key to continue:') # This prompts the user for input
                                                                                                                              # to start the next part of the game
                                                                                                                              # This allows for a break in text
                                                                                                                              # so that all the text does not
                                                                                                                              # appear at once
                                                                                                                              # note, there is no invalid input here
                                                                                                                              # The user can enter anything they
                                                                                                                              # want, so long as they hit enter at
                                                                                                                              # the end, the game will still start
                                                                                                                              # This is deliberate for user ease
                                                                                                                              # of use and to allow the user to be
                                                                                                                              # ready before the start of the game


print('-----------------------------------------------------------------')  # This prints another line of dashes to help with the readability of the code
game_instructions()  # This calls the game instructions function to print
print('-----------------------------------------------------------------')  # This prints another line of dashes to help with the readability of the code

while True:  # This begins the while loop that will contain the running of the game. It will continue until the game is either won or lost
    player_status()  # This calls the player status to print
    print('-----------------------------------------------------------------')  # This prints another line of dashes to help with the readability of the code
    if current_room == 'Research Lab': # This asks if the current room is the Research Lab
        if len(inventory) < 6: # This asks if the length of the inventory is less than 6
            print('Oh no, the research is lost and the robot won! Game Over. Thank you for playing!')  # If the above if statement is correct, this print line
                                                                                                       # is begun
        elif len(inventory) == 6: # This asks if the length of the inventory is equal to 6
            print('Congratulations!!! You vanquished the Evil Robot and saved your research!!! Thank you for playing!')  # If the above is correct, this  print
                                                                                                                         # line is begun
        break  # This allows the code to break the loop if either of the above conditions are met

    user_input = input('Please enter a command:')  # This takes in user input and stores it as a variable
    user_command = user_input.split()  # This splits the user input

    if user_input == '':  # This asks if the user input is empty. This could be instead of typing anything, the user just hits enter
        print('Your command should contain content such as go [compass direction] or get [item name].')  # If the above if statement is correct, this print
                                                                                                         # print line is given

    elif user_command[0] == 'go':  # This asks if the first element in the split user command is go

        if user_command[1] in rooms[current_room]:  # This asks that if the above is correct, is the second element entered in rooms and current room
            current_room = rooms[current_room][user_command[1]]  # If the above is correct, the current_room variable is updated

        else:  # This is initiated if the above conditions are not met. In other words, if the user entered an invalid input
            print('I am sorry, that is not a valid direction and/or you cannot go that way.')  # This lets the user know they entered an invalid input

    elif user_command[0] == 'get':  # This asks if the first element in the split user command is get

        if current_room == 'Recreation Room': # This asks if the current room is the Recreation Room if the first element is get
            print('I am sorry, but there is not an item in this room. Please try another. Use the go command to move between rooms.')  # Since the Recreation Room
                                                                                                                                       # has no items in it,
                                                                                                                                       # if the user tries
                                                                                                                                       # to get an item in this
                                                                                                                                       # room, they will instead
                                                                                                                                       # get this print line.
            continue  # If the room is the Recreation Room and the user tries to get an item, this continue will allow for the loop to restart and new input entered

        if (user_command[1] in rooms[current_room]['item']) and (user_command[1] == rooms[current_room]['item']):  # This asks if the second element in the split
                                                                                                                   # user command is in the rooms dictionary
                                                                                                                   # related to current room and item in the
                                                                                                                   # current room

            if rooms[current_room]['item'] not in inventory:  # This asks if the item in the current room is not in the inventory
                inventory.append(rooms[current_room]['item'])  # If the item in the current room is not in the inventory, it is appended to the inventory
                print(user_command[1], 'has been retrieved! You can now see it in your inventory.')  # This lets the user know the item has been added to
                                                                                                     # the inventory
                continue  # This allows the while loop to start again without going through the other criteria in this elif decision

            if rooms[current_room]['item'] in inventory:  # This asks if the item in the current room is already in the inventory
                print('That item is already in your inventory!')  # If the item is in the inventory and the user tries to put it in again, they will get
                                                                  # this message

        else:  # This is reached if the above if statements for the get command are not correct. This could be something like the user enters an invalid item or
               # the item is not in that room
            print('I am sorry, the item is not in that room or you have entered an invalid item.')  # If the above criteria is correct, then this print statement
                                                                                                    # lets the user know the item is not in the room or it is invalid
            continue  # This allows the while loop to start again without going through the other criteria in this elif decision

    else:  # This else is met if the above criteria are not correct. In other words, if a user enters something other than get or go
        print('I am sorry, that is not a valid command. Please try again.')  # This lets the user know they have entered an invalid command
                                                                             # This could be something like find instead of get or go
