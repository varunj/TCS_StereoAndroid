import Graphics.OpenSCAD
import Prelude hiding (length)

height = 7.4
width = 5
length = 50
thick = 1

main = draw $ rotate (0, -90, 0) mount

mount = union [leg, top, clip]
leg = difference (box width length thick)
      $ union [translate (width / 2, length - 14.5, -0.5) hole,
               translate (width / 2, length - 2, -0.5) hole]
top = box width thick $ height + 1
clip = translate (0, 0, height) $ box width 15 thick
hole = color red . cylinder 1 (thick + 1) $ fn 60
